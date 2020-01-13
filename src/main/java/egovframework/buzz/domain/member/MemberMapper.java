package egovframework.buzz.domain.member;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**************************************************************************************************************
 * 전자정부 프레임워크
 * MyBatis의 경우 Mapper interface 방식으로 사용가능(권장)하며, 
 * 이 경우는 interface 상에 @Mapper를 지정하여 사용되어야 함 
 **************************************************************************************************************/
@Mapper
public interface MemberMapper {
	
	public List<HashMap<String,Object>> memberList();
	public List<HashMap<String,Object>> errorMemberList();
	public void insertDemo(HashMap<String,Object> params);
}
