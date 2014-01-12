package pt.ist.fenixedu.android;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class FenixEduHttpResponseHandler<T> extends
		JsonHttpResponseHandler {

	private final Class<T> clazz;
	private final Gson gson;

	public FenixEduHttpResponseHandler(Class<T> clazz) {
		super();
		this.clazz = clazz;
		this.gson = new Gson();
	}

	public FenixEduHttpResponseHandler(String encoding, Class<T> clazz) {
		super(encoding);
		this.clazz = clazz;
		this.gson = new Gson();
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, String responseBody) {
		T object = gson.fromJson(responseBody, clazz);
		onSuccess(object);
	}

	public abstract void onSuccess(T object);

}
