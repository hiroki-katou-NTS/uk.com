module nts.uk.ui.at.kdp013.a {
    export type API = {
        readonly ADD: string;
        readonly START: string;
        readonly DELETE: string;
        readonly REGISTER: string;
        readonly SELECT: string;
        readonly CHANGE_DATE: string;
        readonly REG_WORKTIME: string;
    };

    export type StartProcessDto = {
        // 工数入力を起動する
        startManHourInputResultDto: StartManHourInputResultDto;

        // 参照可能職場・社員を取得する
        refWorkplaceAndEmployeeDto: GetRefWorkplaceAndEmployeeResultDto;
    };

    export type ProcessInitialStartDto = {
        // 工数入力を起動する
        startManHourInputDto: StartManHourInputResultDto;

        // 参照可能職場・社員を取得する
        refWorkplaceAndEmployeeDto: GetRefWorkplaceAndEmployeeDto;
    };

    export type StartManHourInputResultDto = {
        /** 作業枠利用設定 */
        taskFrameUsageSetting: TaskFrameUsageSettingDto;

        /** List<作業> */
        tasks: c.TaskDto[];

        /** List<勤務場所> */
        workLocations: WorkLocationDto[];
    };

    export type GetRefWorkplaceAndEmployeeResultDto = {

        /** 社員の所属情報(Map<社員ID,職場ID>)*/
        employeeInfos: EmployeInfo[];

        /** List＜社員ID（List）から社員コードと表示名を取得＞*/
        lstEmployeeInfo: EmployeeBasicInfoImport[];

        /** List＜職場情報一覧＞ */
        workplaceInfos: WorkplaceInfoDto[];

    }

    export type WorkplaceInfoDto = {
        /** The history id. */
        historyId: string;

        /** The workplace code. */
        workplaceCode: string;

        /** The workplace name. */
        workplaceName: string;

        /** The wkp generic name. */
        wkpGenericName: string;

        /** The wkp display name. */
        wkpDisplayName: string;

        /** The outside wkp code. */
        outsideWkpCode: string;
    }


    export type GetRefWorkplaceAndEmployeeDto = {
        /** 社員の所属情報(Map<社員ID,職場ID>)*/
        employeeInfos: [];

        /** List＜社員ID（List）から社員コードと表示名を取得＞*/
        lstEmployeeInfo: EmployeeBasicInfoImport[];

        /** List＜職場情報一覧＞ */
        workplaceInfos: WorkplaceInfo[];
    };

    export type EmployeInfo = {

    };

    export type EmployeeBasicInfoImport = {
        sid: string;
        employeeCode: string;
        employeeName: string;
    };

    export type WorkplaceInfo = {

    };

    export type TaskFrameUsageSettingDto = {
        frameSettingList: TaskFrameSettingDto[];
    };

    export type TaskFrameSettingDto = {
        frameNo: number;

        frameName: string | null;

        useAtr: number;
    };

    export type WorkLocationDto = {
        /** 契約コード */
        contractCode: string;

        /** コード */
        workLocationCD: string;

        /** 名称 */
        workLocationName: string;

        /** 打刻範囲 . 半径*/
        radius: number;

        /** 打刻範囲.地理座標.緯度*/
        latitude: number;

        /** 打刻範囲.地理座標.経度*/
        longitude: number;

        /** IPアドレス一覧*/
        listIPAddress: Ipv4AddressDto[];

        /** 職場*/
        listWorkplace: WorkplacePossibleCmd[];
    };

    export type Ipv4AddressDto = {
        /** net1 */
        net1: number;

        /** net2 */
        net2: number;

        /** host1 */
        host1: number;

        /** host2 */
        host2: number;
    };

    export type WorkplacePossibleCmd = {
        /**
         * 会社ID
         */
        companyId: string;

        /**
         * 職場ID
         */
        workpalceId: string;
    };

    export type RegisterWorkContentParam = {


        /** 対象日 */
        date: string;

        /** 対象者 */
        employeeId: string;

        /** 編集状態<Enum.日別勤怠の編集状態> */
        editStateSetting: number;

        /** List<年月日,List<作業詳細>> */
        workDetails: WorkDetailDto[];
    };

    export type WorkDetailDto = {
        /** 年月日 */
        date: string;

        /** List<作業詳細> */
        lstWorkDetailsParam: WorkDetailsParamDto[];
    };

    export type WorkDetailsParamDto = {
        // 応援勤務枠No: 応援勤務枠No
        supportFrameNo: number;

        // 時間帯: 時間帯
        timeZone: TimeZoneDto;

        // 作業グループ
        workGroup: WorkGroupDto;

        // 備考: 作業入力備考
        remarks: string;

        // 勤務場所: 勤務場所コード
        workLocationCD: string;
    };

    export type TimeZoneDto = {

        // 開始時刻: 時刻(日区分付き)
        start: WorkTimeInformationDto;

        // 終了時刻: 時刻(日区分付き)
        end: WorkTimeInformationDto;

        // 作業時間: 勤怠時間
        workingHours: number | null;

    };

    export type WorkGroupDto = {
        /** 作業CD1 */
        workCD1: string;

        /** 作業CD2 */
        workCD2: string;

        /** 作業CD3 */
        workCD3: string;

        /** 作業CD4 */
        workCD4: string;

        /** 作業CD5 */
        workCD5: string;
    };

    export type WorkTimeInformationDto = {
        // 時刻変更理由
        reasonTimeChange: ReasonTimeChangeDto;

        // 時刻  
        timeWithDay: number | null;
    };

    export type ReasonTimeChangeDto = {
        //時刻変更手段
        timeChangeMeans: number | null;

        //打刻方法
        engravingMethod: number | null;
    };

    export type SelectTargetEmployeeParam = {

        // 社員ID
        employeeId: string;

        // 基準日
        refDate: string;

        //表示期間
        displayPeriod: DatePeriodDto;
    };

    export type DatePeriodDto = {
        /** 開始日 */
        start: string;

        /** 終了日 */
        end: string;
    };

    export type SelectTargetEmployeeDto = {
        // よく利用作業一覧：List<作業グループ>
        lstWorkGroupDto: WorkGroupDto[];

        // List＜確認者> 
        lstConfirmerDto: ConfirmerDto[];

        // List<日別勤怠(Work)>
        integrationOfDailyDto: IntegrationOfDailyDto[];

        // 修正可能開始日付
        modifyableStartDate: string;
    };


    export type ConfirmerDto = {
        /** 社員ID */
        confirmSID: string;

        /** 社員コード */
        confirmSCD: string;

        /** ビジネスネーム */
        businessName: string;

        /** 確認日時 */
        confirmDateTime: string;
    };

    export type IntegrationOfDailyDto = {
        // 社員ID
        employeeId: string;

        // 年月日
        ymd: string;

        // 勤務情報: 日別勤怠の勤務情報
        workInformationDto: WorkInfoOfDailyAttendanceDto;
    };

    export type WorkInfoOfDailyAttendanceDto = {
        // 勤務実績の勤務情報
        recordInfoDto: WorkInformationDto;

        // 計算状態
        calculationState: number;

        // 直行区分
        goStraightAtr: number;

        // 直帰区分
        backStraightAtr: number;

        // 曜日
        dayOfWeek: number;

        // 始業終業時間帯
        scheduleTimeSheets: ScheduleTimeSheetDto;

        // 振休振出として扱う日数
        numberDaySuspension: NumberOfDaySuspensionDto;
    };

    export type WorkInformationDto = {
        // 勤務種類コード
        workType: string;
        // 就業時間帯コード
        workTime: string;
    };

    export type ScheduleTimeSheetDto = {
        workNo: number | null;

        attendance: number;

        leaveWork: number;
    };

    export type NumberOfDaySuspensionDto = {
        //振休振出日数
        days: number | null;

        //振休振出区分
        classifiction: number;
    };

    export type RegisterWorkContentCommand = {
        /** 対象者 */
        employeeId: string;

        /** 編集状態<Enum.日別勤怠の編集状態> */
        editStateSetting: number;

        /** List<年月日,List<作業詳細>> */
        workDetails: WorkDetailCommand[];

        /// ????
        mode: number;
    };

    export type WorkDetailCommand = {
        /** 年月日 */
        date: string;

        /** List<作業詳細> */
        lstWorkDetailsParamCommand: WorkDetailsParamCommand[];
    };

    export type WorkDetailsParamCommand = {
        // 応援勤務枠No: 応援勤務枠No
        supportFrameNo: number;

        // 時間帯: 時間帯

        // 作業グループ
        workGroup: WorkGroupDto;

        // 備考: 作業入力備考
        remarks: string;

        // 勤務場所: 勤務場所コード
        workLocationCD: string;
    };

    export type DeleteWorkResultConfirmationCommand = {
        //対象者
        employeeId: string;

        //対象日
        date: string;

        //確認者
        confirmerId: string;
    };

    export type ChangeDateParam = {

        // 社員ID
        employeeId: string;

        // 基準日
        refDate: string;

        // 表示期間
        displayPeriod: DatePeriodDto;
    };

    export type DatePeriodDto = {

    };

    export type AddAttendanceTimeZoneParam = {
        /** 対象者 */
        employeeId: string;

        /** 編集状態<Enum.日別勤怠の編集状態> */
        editStateSetting: number;

        /** List<年月日,List<作業詳細>> */
        workDetails: WorkDetailDto[];
    };
}