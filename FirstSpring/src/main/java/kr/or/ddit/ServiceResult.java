package kr.or.ddit;

/*
 *  ServiceResult인 enum은 처리를 담당하는 컨트롤러 안에서
 *  서비스로 요청한 기능명의 결과로 넘어오는 결과들인데,
 *  처리 성공 여부 또는 처리 여부에 따라서 OK, FAILED, EXIST, NOTEXIST 등의 결과가 넘어온다.  
 */
public enum ServiceResult { // 결과에 대한 내용. 필요한게 있으면 더 추가 가능
	OK, FAILED, EXIST, NOTEXIST
}
