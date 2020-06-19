module nts.uk.at.view.kdp001.a {

    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import alert = nts.uk.ui.dialog.alert;
    import info = nts.uk.ui.dialog.info;
    import confirm = nts.uk.ui.dialog.confirm;
    import dialog = nts.uk.ui.dialog;

    export module viewmodel {
        export class ScreenModel {

            isUsed: KnockoutObservable<boolean> = ko.observable(false);
            setting: KnockoutObservable<IStampSetting> = ko.observable();
            stampDatas: KnockoutObservableArray<any> = ko.observableArray([{
                stampNumber: 1,
                stampDatetime: moment(),
                stampAtr: 'Test',
                stamp: {
                    relieve: {
                        stampMeans: '123123'
                    }
                }

            },
                {
                    stampNumber: 1,
                    stampDatetime: moment(),
                    stampAtr: 'Test',
                    stamp: {
                        relieve: {
                            stampMeans: '123123'
                        }
                    }

                },
                {
                    stampNumber: 1,
                    stampDatetime: moment(),
                    stampAtr: 'Test2',
                    stamp: {
                        relieve: {
                            stampMeans: '123123'
                        }
                    }

                },
                {
                    stampNumber: 1,
                    stampDatetime: moment(),
                    stampAtr: 'Test3',
                    stamp: {
                        relieve: {
                            stampMeans: '123123'
                        }
                    }

                }
                ]);
            systemDate: KnockoutObservable<any> = ko.observable();
            screenMode: KnockoutObservable<any> = ko.observable();
            buttons: KnockoutObservableArray<IButtonSettingsDto> = ko.observableArray([
                {
                    buttonPositionNo: 1,
                    buttonDisSet: {

                        buttonNameSet: {
                            textColor: '#f3f3f3',
                            buttonName: 'test'
                        },
                        /** 背景色 */
                        backGroundColor: '#3e7db6'
                    }
                },
                {
                    buttonPositionNo: 2,
                    buttonDisSet: {

                        buttonNameSet: {
                            textColor: '#f3f3f3',
                            buttonName: 'test2'
                        },
                        /** 背景色 */
                        backGroundColor: '#3e7db6'
                    }
                },
                {
                    buttonPositionNo: 3,
                    buttonDisSet: {

                        buttonNameSet: {
                            textColor: '#f3f3f3',
                            buttonName: 'test3'
                        },
                        /** 背景色 */
                        backGroundColor: '#3e7db6'
                    }
                },
                {
                    buttonPositionNo: 4,
                    buttonDisSet: {

                        buttonNameSet: {
                            textColor: '#f3f3f3',
                            buttonName: 'test4'
                        },
                        /** 背景色 */
                        backGroundColor: '#3e7db6'
                    }
                }
            ]);
            constructor() {
                let self = this;


            }

            /**
             * start page
             */
            public getSystemDate() {
                let self = this,
                    date = self.systemDate().format("YYYY/MM/DD")
                    ;
                return nts.uk.time.applyFormat("Long_YMDW", [date]);
            }

            public getSystemTime() {
                let self = this,
                    time = self.systemDate().format("HH:mm");
                return time;
            }
            public stamp(data) {
                let self = __viewContext['viewModel'],
                    cmd = {
                        datetime: self.systemDate(),

                        buttonPositionNo: data.buttonPositionNo,

                        refActualResults: null

                    }
                    ;

                if (data.buttonPositionNo != 3 && data.buttonPositionNo != 4) {

                    service.registerStampInput(cmd).done(() => {


                    });
                } else {
                    switch (data.buttonPositionNo) {
                        case 3:
                            self.openDialogC();
                            break;
                        case 4:
                            self.openDialogB();
                            break;
                    }

                }

            }

            public openDialogB() {
                let self = this;
                nts.uk.ui.windows.sub.modal('/view/cps/002/b/index.xhtml').onClosed(function(): any {
                    service.getEmployeeStampData().done((data) => {
                        self.stampDatas(data.listStampInfoDisp);
                    });
                });
            }

            public openDialogC() {
                let self = this;

                nts.uk.ui.windows.sub.modal('/view/cps/002/c/index.xhtml').onClosed(function(): any {
                    service.getEmployeeStampData().done((data) => {
                        self.stampDatas(data.listStampInfoDisp);
                    });
                });
            }

            public start_page(): JQueryPromise<any> {

                let self = this, dfd = $.Deferred();

                let url = $(location).attr('search');
                let urlParam: string = url.split("=")[1];
                
                self.screenMode(!!urlParam ? urlParam : null);
                
                service.confirmUseOfStampInput({ stampMeans: 4 }).done((result) => {
                    self.isUsed(result.used != 2);
                    self.systemDate(moment.utc(result.systemDate));
                    setInterval(() => {
                        self.systemDate(self.systemDate().add(1, 'seconds'));
                    }, 1000);
                    service.getSettingStampInput().done((setting: IStampSetting) => {
                        self.setting(setting);
                        if (!!setting) {
                            let buttons;
                            if (self.showButtonGoOutAndBack() && setting.portalStampSettings.buttonSettings.length > 2) {
                                buttons = setting.portalStampSettings.buttonSettings.splice(2);
                            } else {
                                buttons = setting.portalStampSettings.buttonSettings;
                            }
                            self.buttons(_.sortBy(buttons, ['buttonPositionNo']));
                        }
                    });
                    let query = { startDate: moment().format("YYYY/MM/DD"), endDate: moment().format("YYYY/MM/DD") };
                    service.getEmployeeStampData(query).done((data) => {
                        self.stampDatas(data.listStampInfoDisp);
                        if (self.stampDatas().length) {
                            if (self.screenMode() == 'a' || self.screenMode() == 'b') {
                                $("#fixed-table").ntsFixedTable({ height: 53, width: 215 });
                            } else {
                                if (!self.screenMode()) {
                                    $("#fixed-table").ntsFixedTable({ height: 89, width: 280 });
                                }
                            }

                        }
                    });

                    dfd.resolve();

                });
                return dfd.promise();
            }

            public isScreenCD() {
                let self = this;
                return self.screenMode() == 'c' || self.screenMode() == 'd';
            }

            public showButtonGoOutAndBack() {
                let self = this;
                return self.screenMode() == 'b' || self.screenMode() == 'c';
            }

            public showTable() {
                let self = this;
                console.log(self.screenMode());
                return self.screenMode() != 'a' && self.screenMode() != 'b';
            }
            public isWidget(){
                let self =this;
                return !self.screenMode();
            }
        }


    }


    export interface IDialogToMaster {
        commonMasterId: string;
        masterList: Array<ICommonMaster>;
        itemList: Array<IMasterItem>;
        commonMasterItemId: string;

    }

    export interface ICommonMaster {
        //共通マスタID
        commonMasterId: string;
        // 共通マスタコード
        commonMasterCode: string;
        // 共通マスタ名
        commonMasterName: string;
        // 備考
        commonMasterMemo: string;
    }

    export interface IStampSetting {
        portalStampSettings: IPortalStampSettingsDto;

        empInfos: Array<IEmpInfoPotalStampDto>;
    }

    export interface IPortalStampSettingsDto {
        // 会社ID
        cid: String;

        // 打刻画面の表示設定
        displaySettingsStampScreen: IDisplaySettingsStampScreenDto;

        // 打刻ボタン設定
        buttonSettings: Array<IButtonSettingsDto>;

        // 打刻ボタンを抑制する
        suppressStampBtn: boolean;

        // トップメニューリンク利用する
        useTopMenuLink: boolean;
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
        textColor: string;

        /** 背景色 */
        backgroundColor: string;
    }

    export interface IButtonSettingsDto {
        /** ボタン位置NO */
        buttonPositionNo: number;

        /** ボタンの表示設定 */
        buttonDisSet: IButtonDisSetDto;

        /** ボタン種類 */
        buttonType: IButtonTypeDto;

        /** 使用区分 */
        usrArt: number;

        /** 音声使用方法 */
        audioType: number;

    }
    export interface IButtonDisSetDto {
        /** ボタン名称設定 */
        buttonNameSet: IButtonNameSetDto;

        /** 背景色 */
        backGroundColor: String;
    }

    export interface IButtonNameSetDto {
        /** 文字色 */
        textColor: string;

        /** ボタン名称 */
        buttonName: string;
    }

    export interface IButtonTypeDto {
        /** 予約区分 */
        reservationArt: number;

        /** 打刻種類 */
        stampType: IStampTypeDto;
    }

    export interface IStampTypeDto {
        /** 勤務種類を半休に変更する */
        changeHalfDay: boolean;

        /** 外出区分 */
        goOutArt: number;

        /** 所定時刻セット区分 */
        setPreClockArt: number;

        /** 時刻変更区分 */
        changeClockArt: number;

        /** 計算区分変更対象 */
        changeCalArt: number;

    }

    export interface IEmpInfoPotalStampDto {
        /**
         * ・社員の打刻データ(Opt) ← 社員の打刻データ EA3782
         */
        stampDataOfEmp: IStampDataOfEmployeesDto;
        /**
         * ・抑制する打刻(Opt)
         */
        StampToSuppress: IStampToSuppressDto;
    }

    export interface IStampToSuppressDto {
        goingToWork: boolean;
        departure: boolean;
        goOut: boolean;
        turnBack: boolean;
    }

    export interface IStampDataOfEmployeesDto {
        employeeId: String;
        date: String;
        stampRecords: Array<IStampRecordDto>;
    }

    export interface IStampRecordDto {
        stampNumber: String;
        stampDate: String;
        stampTime: String;
        stampHow: String;
        stampArt: String;
        stampArtName: String;
        revervationAtr: number;
        empInfoTerCode: number;
        timeStampType: String;

        // stamp
        authcMethod: number;
        stampMeans: number;

        changeHalfDay: boolean;
        goOutArt: number;
        setPreClockArt: number;
        changeClockArt: number;
        changeClockArtName: String;
        changeCalArt: number;

        cardNumberSupport: String;
        workLocationCD: String;
        workTimeCode: String;
        overTime: String;
        overLateNightTime: String;

        reflectedCategory: boolean;

        locationInfor: IStampLocationInforDto;
        outsideAreaAtr: boolean;
        latitude: number;
        longitude: number;

        attendanceTime: String;


    }
    export interface IStampLocationInforDto {

        outsideAreaAtr: boolean;

        /**
         * 打刻位置情報
         */
        positionInfor: IGeoCoordinateDto;
    }

    export interface IGeoCoordinateDto {
        latitude: number;

        /** 経度 */
        longitude: number;

    }



    export interface IMasterItem {
        // 共通項目ID
        commonMasterItemId: string;
        // 共通項目コード
        commonMasterItemCode: string;
        // 共通項目名
        commonMasterItemName: string;
        // 表示順
        displayNumber: number;
        // 使用開始日
        usageStartDate: string;
        // 使用終了日
        usageEndDate: string;
    }

    export class MasterItem {

        commonMasterItemId: KnockoutObservable<String> = ko.observable();

        commonMasterItemCode: KnockoutObservable<String> = ko.observable();

        commonMasterItemName: KnockoutObservable<String> = ko.observable();
        displayNumber: KnockoutObservable<number> = ko.observable();
        usageStartDate: KnockoutObservable<String> = ko.observable(moment(new Date()).format("YYYY/MM/DD"));
        usageEndDate: KnockoutObservable<String> = ko.observable("9999/12/31");

        constructor(data: IMasterItem) {
            let self = this;
            if (data) {
                self.commonMasterItemId(data.commonMasterItemId);
                self.commonMasterItemCode(data.commonMasterItemCode);
                self.commonMasterItemName(data.commonMasterItemName);
                self.displayNumber(data.displayNumber);
                self.usageStartDate(data.usageStartDate);
                self.usageEndDate(data.usageEndDate);
            }
        }

        updateData(data: IMasterItem) {
            let self = this;
            self.commonMasterItemCode(data.commonMasterItemCode);
            self.commonMasterItemName(data.commonMasterItemName);
            self.displayNumber(data.displayNumber);
            self.usageStartDate(data.usageStartDate);
            self.usageEndDate(data.usageEndDate);
            nts.uk.ui.errors.clearAll();
        }
    }
}