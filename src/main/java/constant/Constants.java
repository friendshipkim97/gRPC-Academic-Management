package constant;

import lombok.Getter;

public class Constants {

    @Getter
    public enum EMainClient{
        ePortNumber("localhost:50050"),
        one("one"),
        two("two"),
        three("three"),
        four("four"),
        five("five"),
        six("six"),
        seven("seven"),
        eight("eight"),
        eSwitchChoiceFail("올바르지 않은 선택입니다."),
        eColon( " : "),
        eMenuStar("***************************************************"),
        eMenuTitle("******************** 학사관리 시스템******************"),
        eMenuGuide("********************번호를 영어로 입력하세요****************"),
        eMenuOne("************(one) : 모든 학생 정보 불러오기**************"),
        eMenuTwo("************(two) : 모든 강좌 정보 불러오기**************"),
        eMenuThree("************(three) : 학생 등록하기***********************"),
        eMenuFour("************(four) : 학생 지우기************************"),
        eMenuFive("************(five) : 과목 등록하기***********************"),
        eMenuSix("************(six) : 과목 지우기************************"),
        eMenuSeven("************(seven) : 수강 신청************************"),
        eMenuEight("************(eight) : 나가기****************************"),
        eEnterTheMenuAgain("메뉴 입력이 잘못되었습니다. 메뉴를 다시 입력해 주세요."),
        eFalse(false);

        private String content;
        private Boolean check;

        EMainClient(String content) { this.content = content; }
        EMainClient(Boolean check) { this.check = check; }

    }

    @Getter
    public enum ECourseView{
        eCourseNumber("강좌 번호 : "),
        eCourseName("강좌 명 : "),
        eProfessorLastName("교수 성 : "),
        eAdvancedName("강좌 선이수 과목 : "),
        eAddCourseSuccessMessage("ADD COURSE SUCCESS"),
        eAddCourseFailMessage("ADD COURSE FAIL"),
        eMenuStar("***************************************************"),
        eMenuAddCourseGuide("********************강좌 정보 입력하기****************"),
        eMenuCourseNumberGuide("강좌 번호를 입력하세요."),
        eMenuProfessorLastNameGuide("교수 성을 입력하세요."),
        eMenuCourseNameGuide("강좌 이름을 입력하세요."),
        eMenuAdvancedCourseNumberGuide("선이수 강좌 번호를 입력하세요. 입력을 마치셨으면 x를 입력하세요."),
        eMenuDeleteCourseGuide("********************지울 강좌 정보 입력하기****************"),
        eDeleteCourseSuccessMessage("DELETE COURSE SUCCESS"),
        eDeleteCourseFailMessage("DELETE COURSE FAIL"),
        eX("x"),
        eSpacing("\n"),
        eNull(null),
        eZero(0),
        eMatchContainNumber(".*[0-9].*"),
        eMatchOnlyNumber("[0-9]+"),
        eCourseNumberMatchMessage("강좌번호에 숫자만 포함되어야 합니다."),
        eProfessorLastNameMatchMessage("교수 성에 숫자가 포함되서는 안됩니다."),
        eCourseNameMatchMessage("강좌 이름에 숫자가 포함되서는 안됩니다."),
        eAdvancedCourseNumberMatchMessage("선이수 강좌번호에 숫자만 포함되어야 합니다.");

        private String content;
        private int number;

        ECourseView(String content) { this.content = content; }
        ECourseView(int number) { this.number = number; }
    }

    @Getter
    public enum EStudentCourseView{
        eApplicationForCourseSuccessMessage("APPLICATION FOR COURSE SUCCESS"),
        eApplicationForCourseFailMessage("APPLICATION FOR COURSE FAIL"),
        eMenuStar("***************************************************"),
        eMenuApplicationForCourseGuide("********************수강신청 정보 입력하기****************"),
        eMenuStudentNumberGuide("학번을 입력하세요."),
        eMenuCourseNumberGuide("강좌번호를 입력하세요."),
        eMatchContainNumber(".*[0-9].*"),
        eMatchOnlyNumber("[0-9]+"),
        eStudentNumberMatchMessage("학생번호에 숫자만 포함할 수 있습니다."),
        eCourseNumberMatchMessage("강좌번호에 숫자만 포함되어야 합니다.");

        private String content;

        EStudentCourseView(String content) { this.content = content; }
    }

    @Getter
    public enum EStudentView{
        eStudentName("학생 명 : "),
        eStudentNumber("학생 번호 : "),
        eStudentMajor("학생 전공 : "),
        eStudentApplicationForCourseNumber("학생 수강 과목 : "),
        eAddStudentSuccessMessage("ADD STUDENT SUCCESS"),
        eAddStudentFailMessage("ADD STUDENT FAIL"),
        eMenuStar("***************************************************"),
        eMenuAddStudentGuide("********************학생 정보 입력하기****************"),
        eMenuStudentNameGuide("학생 이름을 입력하세요."),
        eMenuStudentNumberGuide("학번을 입력하세요."),
        eMenuStudentMajorGuide("전공을 입력하세요."),
        eDeleteStudentSuccessMessage("DELETE STUDENT SUCCESS"),
        eDeleteStudentFailMessage("DELETE STUDENT FAIL"),
        eMenuDeleteStudentGuide("********************지울 학생 정보 입력하기****************"),
        eSpacing("\n"),
        eNull(null),
        eZero(0),
        eMatchContainNumber(".*[0-9].*"),
        eMatchOnlyNumber("[0-9]+"),
        eStudentNameMatchMessage("학생이름에 숫자를 포함할 수 없습니다."),
        eStudentNumberMatchMessage("학생번호에 숫자만 포함할 수 있습니다."),
        eMajorMatchMessage("전공에 숫자를 포함할 수 없습니다.");

        private String content;
        private int number;

        EStudentView(String content) { this.content = content; }
        EStudentView(int number) { this.number = number; }
    }
}
