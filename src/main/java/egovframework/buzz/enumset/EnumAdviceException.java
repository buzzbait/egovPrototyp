package egovframework.buzz.enumset;

public enum EnumAdviceException {
	NOT_FOUND("notfound.do"),	 
	OTHER_EXCEPTION("exception.do"); 
	
	private String url;
	
	private EnumAdviceException(String url) {    
        this.url = url;
    }   
        
    public String getCode() {
    	return name();
    }    
    
    public String getValue() {
    	return this.url;
    }
}
