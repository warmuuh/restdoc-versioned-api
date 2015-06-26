package wrm.rest;

public class Views {
	public class V1 {};
	public class V2 {};
	
	public static Class<?> getVersion(String url){
		if (url.startsWith("/v1"))
			return V1.class;
		else if (url.startsWith("/v2"))
			return V2.class;
		
		throw new IllegalAccessError("Unsupported version");
		
	}
}
