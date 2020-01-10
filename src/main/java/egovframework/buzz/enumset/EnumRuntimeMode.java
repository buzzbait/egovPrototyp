package egovframework.buzz.enumset;

public enum EnumRuntimeMode {
	LOCAL_SERVER("dev"), //개발자 PC
	TEST_SERVER("test"), //테스트 서버
	PROD_SERVER("prod"); //운영 서버
	
	private String title;
	
	private EnumRuntimeMode(String title) {    
        this.title = title;
    }   
        
    public String getCode() {
    	return name();
    }    
    
    public String getValue() {
    	return this.title;
    }
}
