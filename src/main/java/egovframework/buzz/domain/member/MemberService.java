package egovframework.buzz.domain.member;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
/********************************************************************************************
 * 전자정부프레임워크 규정
 * EgovAbstractDAO(EgovMapperDAO) 또는 EgovAbstractServiceImpl(또는 AbstractServiceImpl)) 
 * 클래스를 상속한 구문이 존재하는지 확인
 * 
 * @Mapper 를 정의 해서 사용하기 때문에 EgovMapperDAO 는 필요 없고 Service Layer 에서는
 * EgovAbstractServiceImpl 를 상속하여 사용한다
 * - 별도의 로그설정 없이 EgovAbstractServiceImpl 에 정의된 egovLogger 를 사용 한다
 ********************************************************************************************/
@Service
public class MemberService extends EgovAbstractServiceImpl {

	@Autowired
	private MemberMapper _memberMapper;
	
	public List<HashMap<String,Object>> memberList(){
		
		List<HashMap<String,Object>> members = _memberMapper.memberList();
		return members;
	}
	
	public List<HashMap<String,Object>> errorMemberList(){
		
		this.egovLogger.debug("Service 호출");
		this.leaveaTrace("leaveaTrace.......");
		
		List<HashMap<String,Object>> members = _memberMapper.errorMemberList();
		return members;
	}
}
