package pt.ist.fenixedu.android;

import pt.ist.fenixedu.sdk.FenixEduConfig;

public class FenixEduAndroidClientFactory {

	private static FenixEduAndroidClient instance;

	private FenixEduAndroidClientFactory() {
	}

	public static FenixEduAndroidClient getInstance() {
		if (instance == null) {
			instance = new FenixEduAndroidClient();
		}
		return instance;
	}

	public static void setClientConfig(FenixEduConfig config) {
		// In case you call this first instead of getInstance
		FenixEduAndroidClient client = getInstance();
		client.setConfig(config);
	}

}
