package com.mmerican.DNSTools;

import java.net.*;
import java.util.*;
import java.io.*;

public class DNSFind {
	private InetAddress[] inetHost = null;
	private String urlName;
	
	// no args constructor - send localhost info
	public DNSFind() {
		this("www.google.com");
	}
	
	public DNSFind(String urlName) {
		this.urlName = removeHttp(urlName);
	}
	
	private static String removeHttp(String url) {
		if (url.startsWith("http://")) {
			String s = url.replace("http://", "");
			return s;
		} else if (url.startsWith("https://")) {
			String s = url.replace("https://", "");
			return s;
		}
		return url;
	}
	
	public List<String> DnsLookup() {
		List<String> list = new ArrayList<String>();
		list.add("List of Servers for: " + urlName);
		try {
			inetHost = InetAddress.getAllByName(urlName);
			for (InetAddress a : inetHost) {
				list.add(a.toString());
			}
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
		return list;
	}
}

class DNSFindLoader {
	public static void main(String[] args) {
		String urlName = "";
		System.out.println("Enter Url name to find all associated servers");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			urlName = reader.readLine();
		} catch (IOException e) {
			System.out.println("You did not enter a valid url - www.domainname.com");
			System.exit(-1);
		}
		DNSFind find = new DNSFind(urlName);
		List<String> list = find.DnsLookup();
		for (String s : list) {
			System.out.println(s);
		}
	}
}
