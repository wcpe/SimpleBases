package com.wcpe.SimpleBases.Obj;

import java.util.List;

public class ChatObj {
	public ChatObj() {
	}


	public ChatObj(String permission, String format, int chatlength, String chatlengthMessage, int chatcool,
			String chatcoolMessage, int weight, List<String> replaceAll, boolean ignoreCool, boolean ignoreLength,
			boolean ignoreReplaceAll) {
		super();
		this.permission = permission;
		this.format = format;
		this.chatlength = chatlength;
		this.chatlengthMessage = chatlengthMessage;
		this.chatcool = chatcool;
		this.chatcoolMessage = chatcoolMessage;
		this.weight = weight;
		this.replaceAll = replaceAll;
		this.ignoreCool = ignoreCool;
		this.ignoreLength = ignoreLength;
		this.ignoreReplaceAll = ignoreReplaceAll;
	}


	private String permission;
	private String format;
	private int chatlength;
	private String chatlengthMessage;
	private int chatcool;
	private String chatcoolMessage;
	private int weight;
	private List<String> replaceAll;
	private boolean ignoreCool;
	private boolean ignoreLength;
	private boolean ignoreReplaceAll;

	/**
	 * @return permission
	 */
	public final String getPermission() {
		return permission;
	}

	/**
	 * @param permission 要设置的 permission
	 */
	public final void setPermission(String permission) {
		this.permission = permission;
	}

	/**
	 * @return format
	 */
	public final String getFormat() {
		return format;
	}

	/**
	 * @param format 要设置的 format
	 */
	public final void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return chatlength
	 */
	public final int getChatlength() {
		return chatlength;
	}

	/**
	 * @param chatlength 要设置的 chatlength
	 */
	public final void setChatlength(int chatlength) {
		this.chatlength = chatlength;
	}

	/**
	 * @return chatlengthMessage
	 */
	public final String getChatlengthMessage() {
		return chatlengthMessage;
	}

	/**
	 * @param chatlengthMessage 要设置的 chatlengthMessage
	 */
	public final void setChatlengthMessage(String chatlengthMessage) {
		this.chatlengthMessage = chatlengthMessage;
	}

	/**
	 * @return chatcool
	 */
	public final int getChatcool() {
		return chatcool;
	}

	/**
	 * @param chatcool 要设置的 chatcool
	 */
	public final void setChatcool(int chatcool) {
		this.chatcool = chatcool;
	}

	/**
	 * @return chatcoolMessage
	 */
	public final String getChatcoolMessage() {
		return chatcoolMessage;
	}

	/**
	 * @param chatcoolMessage 要设置的 chatcoolMessage
	 */
	public final void setChatcoolMessage(String chatcoolMessage) {
		this.chatcoolMessage = chatcoolMessage;
	}

	/**
	 * @return weight
	 */
	public final int getWeight() {
		return weight;
	}

	/**
	 * @param weight 要设置的 weight
	 */
	public final void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return replaceAll
	 */
	public final List<String> getReplaceAll() {
		return replaceAll;
	}

	/**
	 * @param replaceAll 要设置的 replaceAll
	 */
	public final void setReplaceAll(List<String> replaceAll) {
		this.replaceAll = replaceAll;
	}

	@Override
	public String toString() {
		return "ChatObj [permission=" + permission + ", format=" + format + ", chatlength=" + chatlength
				+ ", chatlengthMessage=" + chatlengthMessage + ", chatcool=" + chatcool + ", chatcoolMessage="
				+ chatcoolMessage + ", weight=" + weight + ", replaceAll=" + replaceAll + "]";
	}


	/**
	 * @return ignoreCool
	 */
	public final boolean isIgnoreCool() {
		return ignoreCool;
	}


	/**
	 * @param ignoreCool 要设置的 ignoreCool
	 */
	public final void setIgnoreCool(boolean ignoreCool) {
		this.ignoreCool = ignoreCool;
	}


	/**
	 * @return ignoreLength
	 */
	public final boolean isIgnoreLength() {
		return ignoreLength;
	}


	/**
	 * @param ignoreLength 要设置的 ignoreLength
	 */
	public final void setIgnoreLength(boolean ignoreLength) {
		this.ignoreLength = ignoreLength;
	}


	/**
	 * @return ignoreReplaceAll
	 */
	public final boolean isIgnoreReplaceAll() {
		return ignoreReplaceAll;
	}


	/**
	 * @param ignoreReplaceAll 要设置的 ignoreReplaceAll
	 */
	public final void setIgnoreReplaceAll(boolean ignoreReplaceAll) {
		this.ignoreReplaceAll = ignoreReplaceAll;
	}

}
