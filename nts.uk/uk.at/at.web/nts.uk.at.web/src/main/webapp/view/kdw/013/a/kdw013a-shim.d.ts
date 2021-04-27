module nts.uk.ui.at.kdp013.a {
    export type API = {
        readonly ADD: string;
        readonly DELETE: string;
    };

    export type StartProcessDto = {

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

    export type GetRefWorkplaceAndEmployeeDto = {
        /** 社員の所属情報(Map<社員ID,職場ID>)*/
        employeeInfos: [];

        /** List＜社員ID（List）から社員コードと表示名を取得＞*/
        lstEmployeeInfo: EmployeeBasicInfoImport[];

        /** List＜職場情報一覧＞ */
        workplaceInfos: WorkplaceInfo[];
    };

    export type EmployeeBasicInfoImport = {

    };

    export type WorkplaceInfo = {

    };

    export type TaskFrameUsageSettingDto = {

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
}