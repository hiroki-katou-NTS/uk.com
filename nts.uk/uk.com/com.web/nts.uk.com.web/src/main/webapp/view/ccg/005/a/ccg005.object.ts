module nts.uk.at.view.ccg005.a.object {
  export interface DisplayAttendanceDataDto {
    favoriteSpecifyDto: FavoriteSpecifyDto[];               //お気に入りの指定
    attendanceInformationDtos: AttendanceInformationDto[];  //在席情報DTO
    emojiUsage: number;                                     //感情状態を利用する
    inCharge: boolean;                                      //担当者か
    applicationNameDtos: ApplicationNameDto[];              //申請名
    bussinessName: string;                                  //自分のビジネスネーム
  }

  export interface FavoriteSpecifyDto {
    favoriteName: string;                                   // お気に入り名
    creatorId: string;                                      // 作��D
    inputDate: any;                                         // 入力日;
    targetSelection: number;                                // 対象選�
    workplaceId: string[];                                  // 職場ID
    order: number;                                          // 頺
  }

  export interface AttendanceInformationDto {
    applicationDtos: any[];                                 //申請
    sid: string;                                            //社員ID
    attendanceDetailDto: any;                               //詳細出退勤
    avatarDto: UserAvatarDto;                               //個人の顔写真
    activityStatusDto: number;                              //在席のステータス
    commentDto: any;                                        //社員のコメント情報
    goOutDto: GoOutEmployeeInformationDto;                  //社員の外出情報
    emojiDto: EmployeeEmojiStateDto;                        //社員の感情状態
  }

  export interface ApplicationNameDto {
    appName: string;                                        //申請名
    appType: number;                                        //申請種類
  }

  export interface UserAvatarDto {
    personalId: string;                                     //個人ID
    fileId: string;                                         //顔写真ファイルID
  }

  export interface GoOutEmployeeInformationDto {
    goOutTime: number;                                      //外出時刻
    goOutReason: string;                                    //外出理由
    goOutDate: any;                                         //年月日
    comebackTime: number;                                   //戻り時刻
    sid: string;                                            //社員ID
  }

  export interface EmployeeEmojiStateDto {
    date: any;                                              //年月日
    emojiType: number;                                      //感情種類
    sid: string;                                            //社員ID
  }

  export interface DisplayInformationDto {
    attendanceInformationDtos: AttendanceInformationDto[],  //在席情報DTO（List）
    listPersonalInfo: EmployeeBasicImport[]                 //個人基本情報（List）
  }

  export interface EmployeeBasicImport {
    employeeId: string,                                     //社員ID
    personalId: string,                                     //個人ID
    businessName: string,                                   //ビジネスネーム
    employeeCode: string                                    //社員コード
  }

  export interface EmployeeCommentInformationDto {
    comment: string;                                        // コメント
    date: any;                                              // 年月日
    sid: string;                                            // 社員ID
  }

  export enum StartMode {
    WORKPLACE = 0,
    DEPARTMENT = 1
  }

  export enum SystemType {
    PERSONAL_INFORMATION = 1,
    EMPLOYMENT = 2,
    SALARY = 3,
    HUMAN_RESOURCES = 4,
    ADMINISTRATOR = 5
  }

  export enum EmojiType {
    WEARY = 0, // どんより: アイコン#189
    SAD = 1, // ゆううつ: アイコン#188
    AVERAGE = 2, // 普通: アイコン#187
    GOOD = 3, // ぼちぼち: アイコン#186
    HAPPY = 4 // いい感じ: アイコン#185
  }

  export enum Emoji {
    WEARY = 189, // どんより: アイコン#189
    SAD = 188, // ゆううつ: アイコン#188
    AVERAGE = 187, // 普通: アイコン#187
    GOOD = 186, // ぼちぼち: アイコン#186
    HAPPY = 185 // いい感じ: アイコン#185
  }

  export enum StatusClassfication {
    NOT_PRESENT = 0, // 未出社: アイコン#196
    PRESENT = 1, // 在席: アイコン#195
    GO_OUT = 2, // 外出: アイコン#191
    GO_HOME = 3, // 帰宅: アイコン#196
    HOLIDAY = 4 // 休み: アイコン#197
  }

  export enum StatusClassficationIcon {
    NOT_PRESENT = 196, // 未出社: アイコン#196
    PRESENT = 195, // 在席: アイコン#195
    GO_OUT = 191, // 外出: アイコン#191
    GO_HOME = 196, // 帰宅: アイコン#196
    HOLIDAY = 197 // 休み: アイコン#197
  }
}