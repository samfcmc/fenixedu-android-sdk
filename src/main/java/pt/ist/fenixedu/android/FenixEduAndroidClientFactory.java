package pt.ist.fenixedu.android;

import pt.ist.fenixedu.sdk.FenixEduConfig;

/**
 * A factory for creating FenixEduAndroidClient objects.
 */
public class FenixEduAndroidClientFactory {

	/** The instance. */
	private static FenixEduAndroidClient instance;

	/**
	 * The instance config. This config object will be passed to instance in
	 * getInstance call
	 */
	private static FenixEduConfig instanceConfig;

	/**
	 * Instantiates a new fenix edu android client factory.
	 */
	private FenixEduAndroidClientFactory() {
	}

	/**
	 * Gets the single instance of FenixEduAndroidClientFactory.
	 * 
	 * @return single instance of FenixEduAndroidClientFactory
	 */
	public static FenixEduAndroidClient getInstance() {
		if (instance == null) {
			instance = new FenixEduAndroidClient(instanceConfig);
		}
		return instance;
	}

	/**
	 * Sets the client config.
	 * 
	 * @param config
	 *            the new client config
	 */
	public static void setClientConfig(FenixEduConfig config) {
		instanceConfig = config;
	}

}
