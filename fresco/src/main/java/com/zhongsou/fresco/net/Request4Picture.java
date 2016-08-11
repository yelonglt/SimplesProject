package com.zhongsou.fresco.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.reflect.TypeToken;
import com.zhongsou.fresco.model.Picture;

import org.json.JSONObject;

import java.util.ArrayList;


public class Request4Picture extends Request<ArrayList<Picture>> {

	private Response.Listener<ArrayList<Picture>> listener;

	public Request4Picture(String url, Response.Listener<ArrayList<Picture>> listener,
	                       Response.ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.listener = listener;
	}

	@Override
	protected Response<ArrayList<Picture>> parseNetworkResponse(NetworkResponse response) {

		try {
			String jsonStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			JSONObject jsonData=new JSONObject(jsonStr);

			jsonStr = jsonData.getJSONArray("tngou").toString();

			ArrayList<Picture> pictures = (ArrayList<Picture>) JSONParser.toObject(jsonStr,
					new TypeToken<ArrayList<Picture>>() {
					}.getType());

			return Response.success(pictures, HttpHeaderParser.parseCacheHeaders(response));

		} catch (Exception e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(ArrayList<Picture> response) {
		listener.onResponse(response);
	}

}
