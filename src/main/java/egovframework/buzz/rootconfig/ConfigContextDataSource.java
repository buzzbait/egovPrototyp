package egovframework.buzz.rootconfig;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class ConfigContextDataSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigContextDataSource.class);
	/********************************************************************************************************
	 * Spring2.* 이후 부터 기본으로 채택된 HikariCP 를 적용한다
	 * 개발자 환경(local),테스트서버 환경(dev),운영서버(real) 환경을 구분 한다.
	 * 운영환경은 JNDI 로 배포 한다  
	 *******************************************************************************************************/
	
	@Value("${database.url}")
	private String _jdbcUrl;
	
	@Value("${database.username}")
	private String _userName;
	
	@Value("${database.userpass}")
	private String _userPass;
	
	@Value("${database.jndiname}")
	private String _jnidName;
	
	
	@Bean(destroyMethod =  "close")
	@Profile({"dev",  "test"})
	public DataSource devDataSource() {
		
		LOGGER.debug("DataSource Connect..............{}",_jdbcUrl );
		HikariConfig config = new HikariConfig();

		config.setDriverClassName("org.mariadb.jdbc.Driver");
		config.setJdbcUrl(_jdbcUrl);
		config.setUsername(_userName);
		config.setPassword(_userPass);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		HikariDataSource hikariDataSource = new HikariDataSource(config);		
		
		return hikariDataSource;
	}
	
	@Bean(destroyMethod =  "close")
	@Profile("prod")
	public DataSource prodDataSource() {		
		 JndiDataSourceLookup lookup = new JndiDataSourceLookup();
		 return lookup.getDataSource(_jnidName);
	}
	
	
}
