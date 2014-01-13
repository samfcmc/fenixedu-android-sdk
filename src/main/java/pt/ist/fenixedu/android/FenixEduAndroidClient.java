package pt.ist.fenixedu.android;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ist.fenixedu.sdk.FenixEduConfig;
import pt.ist.fenixedu.sdk.beans.publico.FenixAbout;
import pt.ist.fenixedu.sdk.beans.publico.FenixSpace;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class FenixEduAndroidClient {
	private FenixEduConfig config;

	private final AsyncHttpClient client;

	/** The Constant PUBLIC_BASE. */
	private static final String PUBLIC_BASE = "/api/fenix/v1/";

	/** The Constant PRIVATE_BASE. */
	private static final String PRIVATE_BASE = PUBLIC_BASE;

	public FenixEduAndroidClient() {
		this.client = new AsyncHttpClient();
	}

	public FenixEduAndroidClient(FenixEduConfig config) {
		this();
		this.config = config;
	}

	public FenixEduConfig getConfig() {
		return config;
	}

	public void setConfig(FenixEduConfig config) {
		this.config = config;
	}

	/**
	 * Private endpoint.
	 * 
	 * @param path
	 *            the path
	 * @return the string
	 */
	private final String privateEndpoint(String path) {
		return PRIVATE_BASE + path;
	}

	/**
	 * Public endpoint.
	 * 
	 * @param path
	 *            the path
	 * @return the string
	 */
	private final String publicEndpoint(String path) {
		return PUBLIC_BASE + path;
	}

	private final String completeUrl(String endpoint) {
		return config.getBaseUrl() + endpoint;
	}

	private String addParams(String url, Map<String, String> params) {
		String result = url + '?';

		for (java.util.Map.Entry<String, String> entry : params.entrySet()) {
			result += entry.getKey() + "=" + entry.getValue() + "&";
		}

		return result;
	}

	public String getAuthenticationUrl() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("client_id", this.config.getConsumerKey());
		paramMap.put("redirect_uri", this.config.getCallbackUrl());

		String url = String.format("%s/oauth/userdialog", config.getBaseUrl());
		String completeUrl = addParams(url, paramMap);

		return completeUrl;
	}

	public void setCode(String code,
			FenixEduHttpResponseHandler<Void> responseHandler) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("client_id", this.config.getConsumerKey());
		paramsMap.put("redirect_uri", this.config.getCallbackUrl());
		paramsMap.put("client_secret", this.config.getConsumerSecret());
		paramsMap.put("code", code);
		paramsMap.put("grant_type", "authorization_code");

		RequestParams params = new RequestParams(paramsMap);

		String token_url = String.format("%s/oauth/access_token",
				this.config.getBaseUrl());

		client.post(token_url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				String accessToken;
				String refreshToken;
				try {
					accessToken = json.getString("access_token");
					refreshToken = json.getString("refresh_token");
					config.setAccessToken(accessToken);
					config.setRefreshToken(refreshToken);
					// TODO: Handle refresh
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	public void getAbout(FenixEduHttpResponseHandler<FenixAbout> responseHandler) {
		String url = publicEndpoint("about");
		client.get(url, responseHandler);
	}

	public void getSpaces(
			FenixEduHttpResponseHandler<FenixSpace[]> responseHandler) {

		String url = completeUrl(publicEndpoint("spaces"));
		client.get(url, responseHandler);
	}

	public void getSpace(String spaceId, String day,
			FenixEduHttpResponseHandler<? extends FenixSpace> responseHandler) {
		String url = completeUrl(publicEndpoint("spaces/" + spaceId));
		RequestParams params = new RequestParams("day", day);
		client.get(url, params, responseHandler);
	}
}
