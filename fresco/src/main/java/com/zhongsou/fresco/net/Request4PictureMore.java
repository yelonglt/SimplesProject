package com.zhongsou.fresco.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.reflect.TypeToken;
import com.zhongsou.fresco.model.PictureShow;

import org.json.JSONObject;

import java.util.ArrayList;

public class Request4PictureMore extends Request<ArrayList<PictureShow>> {

	private Response.Listener<ArrayList<PictureShow>> listener;

	public Request4PictureMore(String url, Response.Listener<ArrayList<PictureShow>> listener,
							   Response.ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.listener = listener;
	}

	@Override
	protected Response<ArrayList<PictureShow>> parseNetworkResponse(NetworkResponse response) {

		try {
			String jsonStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			JSONObject jsonData=new JSONObject(jsonStr);

			jsonStr = jsonData.getJSONArray("list").toString();

			ArrayList<PictureShow> pictures = (ArrayList<PictureShow>) JSONParser.toObject(jsonStr,
					new TypeToken<ArrayList<PictureShow>>() {
					}.getType());

			return Response.success(pictures, HttpHeaderParser.parseCacheHeaders(response));

		} catch (Exception e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(ArrayList<PictureShow> response) {
		listener.onResponse(response);
	}

}
