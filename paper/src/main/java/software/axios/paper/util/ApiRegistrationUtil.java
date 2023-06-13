package software.axios.paper.util;

import software.axios.api.Axios;
import software.axios.api.AxiosProvider;

import java.lang.reflect.Method;

public class ApiRegistrationUtil {
	private static final Method REGISTER;
	private static final Method UNREGISTER;
	static {
		try {
			REGISTER = AxiosProvider.class.getDeclaredMethod("register", Axios.class);
			REGISTER.setAccessible(true);
			
			UNREGISTER = AxiosProvider.class.getDeclaredMethod("unregister");
			UNREGISTER.setAccessible(true);
		} catch (NoSuchMethodException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static void registerProvider(Axios axiosApi) {
		try {
			REGISTER.invoke(null, axiosApi);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void unregisterProvider() {
		try {
			UNREGISTER.invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
