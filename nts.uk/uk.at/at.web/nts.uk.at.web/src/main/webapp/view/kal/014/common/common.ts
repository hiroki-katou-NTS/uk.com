module nts.uk.at.kal014.common{

    // define WORKPLACE CATAGORY
    export enum WORKPLACE_CATAGORY {
        // マスタチェック(基本)
        MASTER_CHECK_BASIC = <number> 0,
        // マスタチェック(職場)
        MASTER_CHECK_WORKPLACE = <number> 1,
        // マスタチェック(日次)
        MASTER_CHECK_DAILY = <number> 2,
        // スケジュール／日次
        SCHEDULE_DAILY = <number> 3,
        // 月次
        MONTHLY = <number> 4,
        // 申請承認
        APPLICATION_APPROVAL = <number> 5
    }
}