
export module model {
    export interface ISettingSmartPhone {
        //スマホ打刻の打刻設定
        setting: ISettingsSmartphoneStamp;

        //打刻後の実績表示
        resulDisplay: IStampResultDisplay;

        //抑制する打刻
        stampToSuppress: IStampToSuppress;
    }

    export interface ISettingsSmartphoneStamp {
        // 会社ID
        cid: string;

        // 打刻画面の表示設定
        displaySettingsStampScreen: IDisplaySettingsStampScreenDto;

        // ページレイアウト設定
        pageLayoutSettings: Array<IStampPageLayoutDto>;

        // 打刻ボタンを抑制する
        buttonEmphasisArt: boolean;

    }

    export interface IStampPageLayoutDto {
        /** ページNO */
        pageNo: number;

        /** ページ名 */
        stampPageName: string;

        /** ページコメント */
        stampPageComment: IStampPageCommentDto;

        /** ボタン配置タイプ */
        buttonLayoutType: number;

        /** ボタン詳細設定リスト */
        lstButtonSet: Array<ButtonSettingsDto>;
    }

    export interface IStampPageCommentDto {
        /** コメント */
        pageComment: string;

        /** コメント色 */
        commentColor: string;
    }

    export interface ButtonSettingsDto {
        /** ボタン位置NO */
        buttonPositionNo: number;

        /** ボタンの表示設定 */
        buttonDisSet: IButtonDisSetDto;

        buttonValueType: number;
    }

    export interface IButtonDisSetDto {
        /** ボタン名称設定 */
        buttonNameSet: IButtonNameSetDto;

        /** 背景色 */
        backGroundColor: string;
    }

    export interface IButtonNameSetDto {
        /** 文字色 */
        textColor: string;

        /** ボタン名称 */
        buttonName: string;
    }

    export interface IDisplaySettingsStampScreenDto {
        /** 打刻画面のサーバー時刻補正間隔 */
        serverCorrectionInterval: number;

        /** 打刻画面の日時の色設定 */
        settingDateTimeColor: ISettingDateTimeColorOfStampScreenDto;

        /** 打刻結果自動閉じる時間 */
        resultDisplayTime: number;

    }

    export interface ISettingDateTimeColorOfStampScreenDto {
        /** 文字色 */
        textColor: String;

        /** 背景色 */
        backgroundColor: String;

    }



    export interface IStampResultDisplay {
        /** 会社ID */
        companyId: string;

        /** 使用区分 */
        usrAtr: number;

        /** 表示項目一覧 */
        lstDisplayItemId: Array<IStampAttenDisplay>;

    }

    export interface IStampAttenDisplay {
        /** 会社ID */
        companyId: string;

        /** 表示項目一覧 */
        displayItemId: number;
    }

    export interface IStampToSuppress {
        // 出勤
        goingToWork: boolean;

        // 退勤
        departure: boolean;

        // 外出 
        goOut: boolean;

        // 戻り
        turnBack: boolean;
    }

    export interface ICheckStampResult {

        // 打刻カード番号
        cardNumber: string;
        // 打刻利用可否
        used: number;
    }
}