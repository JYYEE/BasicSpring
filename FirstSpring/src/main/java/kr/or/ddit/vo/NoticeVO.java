package kr.or.ddit.vo;

import lombok.Data;

@Data
public class NoticeVO {
	private int boNo;
	private String boTitle;
	private String boContent;
	private String boWriter;
	private String boDate;
	private int boHit;
}
