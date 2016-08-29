package com.picc.chexian.admin.dto;

import lombok.Data;

@Data
public class TreeNode {
	private String id;
	private String pId;
	private String name;
	private String t;
	private boolean open;
}
