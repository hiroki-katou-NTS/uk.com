module nts.uk.at.view.kdp010.g {
    export module viewmodel {
        export class ScreenModel {

            // G2_2
            optionPage: KnockoutObservableArray<any> = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KDP010_98","1") },
                { code: 2, name: nts.uk.resource.getText("KDP010_98","2") },
                { code: 3, name: nts.uk.resource.getText("KDP010_98","3") },
                { code: 4, name: nts.uk.resource.getText("KDP010_98","4") },
                { code: 5, name: nts.uk.resource.getText("KDP010_98","5") }]);
            selectedPage: KnockoutObservable<number> = ko.observable(1);

            // G3_2
            pageName: KnockoutObservable<string> = ko.observable("");

            // G4_2
            optionLayout: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ButtonLayoutType);
            selectedLayout: KnockoutObservable<number> = ko.observable(0);

            // G5_2
            commentDaily: KnockoutObservable<string> = ko.observable("");

            // G6_2
            letterColors: KnockoutObservable<string> = ko.observable('#000000');
            
            currentItem: KnockoutObservable<model.StampPageLayoutCommand> = ko.observable(new model.StampPageLayoutCommand({}));
            
            dataKdpH: KnockoutObservable<model.StampPageCommentCommand> = ko.observable(new model.StampPageCommentCommand({}));
            
            dataShare: KnockoutObservableArray<any> = ko.observableArray([]);

            constructor() {
                let self = this;

            }
            /**
             * start page  
             */
            public startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                self.getData();
                dfd.resolve();
                return dfd.promise();
            }
            getData(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.getStampPage(self.selectedPage()).done(function(totalTimeArr) {
                    if (totalTimeArr) {
                        self.selectedPage(totalTimeArr.pageNo);
                        self.pageName(totalTimeArr.stampPageName);
                        self.commentDaily(totalTimeArr.stampPageComment.pageComment);
                        self.letterColors(totalTimeArr.stampPageComment.commentColor);
                        self.selectedLayout(totalTimeArr.buttonLayoutType)
                        
                        self.dataShare = totalTimeArr;
                    }
                    dfd.resolve();
                }).fail(function(error) {
                    alert(error.message);
                    dfd.reject(error);
                });
                return dfd.promise();
            }

            /**
             * Pass Data to A
             */
            public registration() {
                let self = this;
                nts.uk.ui.block.invisible();
                // Data from Screen 
                let lstButton = new Array<ButtonSettingsCommand>(); 
                let lstButtonSet = new model.ButtonSettingsCommand({
                    buttonPositionNo: self.dataKdpH.buttonPositionNo,
                    buttonDisSet: new model.ButtonDisSetCommand({
                        buttonNameSet: new model.ButtonNameSetCommand({
                            textColor: self.dataKdpH.buttonDisSet.buttonNameSet.textColor,
                            buttonName: self.dataKdpH.buttonDisSet.buttonNameSet.buttonName
                        }),
                        backGroundColor: self.dataKdpH.buttonDisSet.backGroundColor
                    }),
                    buttonType: new model.ButtonTypeCommand({
                        reservationArt: self.dataKdpH.buttonType.reservationArt,
                        stampType: new model.StampTypeCommand({
                            changeHalfDay: self.dataKdpH.buttonType.stampType.changeHalfDay,
                            goOutArt: self.dataKdpH.buttonType.stampType.goOutArt,
                            setPreClockArt: self.dataKdpH.buttonType.stampType.setPreClockArt,
                            changeClockArt: self.dataKdpH.buttonType.stampType.changeClockArt,
                            changeCalArt: self.dataKdpH.buttonType.stampType.changeCalArt
                        })
                    }),
                    usrArt: self.dataKdpH.usrArt,
                    audioType: self.dataKdpH.audioType  
                });
                lstButton.push(lstButtonSet);
                let data = new model.StampPageLayoutCommand({
                    pageNo: self.selectedPage(),
                    stampPageName: self.pageName(),
                    stampPageComment: new model.StampPageCommentCommand({
                        pageComment: self.commentDaily(),
                        commentColor: self.letterColors(),
                    }),
                    buttonLayoutType: self.selectedLayout(),
                    lstButtonSet: lstButton
                });
                service.saveStampPage(data).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }

            /**
             * Close dialog
             */
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            openHDialog(enumVal: number, data): void {
                let self = this;
                let dataG = {
                    dataShare: self.dataShare,
                    buttonPositionNo: enumVal
                }
                nts.uk.ui.windows.setShared('KDP010_G', dataG);
                nts.uk.ui.windows.sub.modal("/view/kdp/010/h/index.xhtml").onClosed(() => { 
                self.dataKdpH = nts.uk.ui.windows.getShared('KDP010_H');
                    self.dataShare = self.dataKdpH;
                });
            }
        }

    }
    export module model {
        // StampPageLayoutCommand
        export class StampPageLayoutCommand {

            /** ページNO */
            pageNo: number;
            /** ページ名 */
            stampPageName: string;
            /** ページコメント */
            stampPageComment: StampPageCommentCommand;
            /** ボタン配置タイプ */
            buttonLayoutType: number;
            /** ボタン詳細設定リスト */
            lstButtonSet: Array<ButtonSettingsCommand>;

            constructor(param: IStampPageLayoutCommand) {
                this.pageNo = param.pageNo;
                this.stampPageName = param.stampPageName;
                this.stampPageComment = param.stampPageComment;
                this.buttonLayoutType = param.buttonLayoutType;
                this.lstButtonSet = param.lstButtonSet;
            }
        }

        interface IStampPageLayoutCommand {
            pageNo: number;
            stampPageName: number;
            stampPageComment: StampPageCommentCommand;
            buttonLayoutType: number;
            lstButtonSet: Array<ButtonSettingsCommand>;
        }
        
        // StampPageCommentCommand
        export class StampPageCommentCommand {

            /** ページNO */
            pageComment: string;
            /** ページ名 */
            commentColor: string;

            constructor(param: IStampPageCommentCommand) {
                this.pageComment = param.pageComment;
                this.commentColor = param.commentColor;
            }
        }

        interface IStampPageCommentCommand {
            pageComment: number;
            commentColor: number;
        }
        
        // ButtonSettingsCommand
        export class ButtonSettingsCommand {
            /** ボタン位置NO */
            buttonPositionNo: number;
            /** ボタンの表示設定 */
            buttonDisSet: ButtonDisSetCommand;
            /** ボタン種類 */
            buttonType: ButtonTypeCommand;
            /** 使用区分 */
            usrArt: number;
            /** 音声使用方法 */
            audioType: number;

            constructor(param: IButtonSettingsCommand) {
                this.buttonPositionNo = param.buttonPositionNo;
                this.buttonDisSet = param.buttonDisSet;
                this.buttonType = param.buttonType;
                this.usrArt = param.usrArt;
                this.audioType = param.audioType;
            }
        }

        interface IButtonSettingsCommand {
             /** ボタン位置NO */
            buttonPositionNo: number;
            /** ボタンの表示設定 */
            buttonDisSet: ButtonDisSetCommand;
            /** ボタン種類 */
            buttonType: ButtonTypeCommand;
            /** 使用区分 */
            usrArt: number;
            /** 音声使用方法 */
            audioType: number;
        }
        
        // ButtonDisSetCommand
        export class ButtonDisSetCommand {

           /** ボタン名称設定 */
            buttonNameSet: ButtonNameSetCommand;
            /** 背景色 */
            backGroundColor: string;

            constructor(param: IButtonDisSetCommand) {
                this.buttonNameSet = param.buttonNameSet;
                this.backGroundColor = param.backGroundColor;
            }
        }

        interface IButtonDisSetCommand {
            buttonNameSet: ButtonNameSetCommand;
            backGroundColor: string;
        }
        
        // ButtonNameSetCommand
         export class ButtonNameSetCommand {

           /** ボタン名称設定 */
            textColor: string;
            /** 背景色 */
            buttonName: string;

            constructor(param: IButtonNameSetCommand) {
                this.textColor = param.textColor;
                this.buttonName = param.buttonName;
            }
        }

        interface IButtonNameSetCommand {
            textColor: string;
            buttonName: string;
        }
        
        // ButtonTypeCommand
        export class ButtonTypeCommand {

            /** 予約区分 */
            reservationArt: number;
            /** 打刻種類 */
            stampType: StampTypeCommand;

            constructor(param: IButtonTypeCommand) {
                this.reservationArt = param.reservationArt;
                this.stampType = param.stampType;
            }
        }

        interface IButtonTypeCommand {
            reservationArt: number;
            stampType: StampTypeCommand;
        }
        
        // StampTypeCommand
        export class StampTypeCommand {

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

            constructor(param: IStampTypeCommand) {
                this.changeHalfDay = param.changeHalfDay;
                this.goOutArt = param.goOutArt;
                this.setPreClockArt = param.setPreClockArt;
                this.changeClockArt = param.changeClockArt;
                this.changeCalArt = param.changeCalArt;
            }
        }

        interface IStampTypeCommand {
            changeHalfDay: boolean;
            goOutArt: number;
            setPreClockArt: number;
            changeClockArt: number;
            changeCalArt: number;
        }

        export enum ButtonLayoutType {
            //就業
            大2小4 = 0,
            小8 = 1
        }
    }
}