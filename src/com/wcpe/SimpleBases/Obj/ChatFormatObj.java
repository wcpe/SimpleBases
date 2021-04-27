package com.wcpe.SimpleBases.Obj;

public class ChatFormatObj {
	public ChatFormatObj() {
	}

	public ChatFormatObj(String message, String command, String list) {
		this.message = message;
		this.command = command;
		this.list = list;
	}

	private String message;
	private String command;
	private String list;

	/**
	 * @return command
	 */
	public final String getCommand() {
		return command;
	}

	/**
	 * @param command 要设置的 command
	 */
	public final void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return list
	 */
	public final String getList() {
		return list;
	}

	/**
	 * @param list 要设置的 list
	 */
	public final void setList(String list) {
		this.list = list;
	}

	/**
	 * @return message
	 */
	public final String getMessage() {
		return message;
	}

	/**
	 * @param message 要设置的 message
	 */
	public final void setMessage(String message) {
		this.message = message;
	}

}