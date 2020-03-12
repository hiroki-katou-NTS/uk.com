module nts.uk.at.view.kdp010.h {
    export module viewmodel {
        export class ScreenModel {

            // H1_2
            optionHighlight: KnockoutObservableArray<any> = ko.observableArray([
                { id: 0, name: nts.uk.resource.getText("KDP010_108") },
                { id: 1, name: nts.uk.resource.getText("KDP010_109") }
            ]);
            selectedHighlight: KnockoutObservable<number> = ko.observable(0);
            
            // H2_2
            dayOfWeek: KnockoutObservableArray<any> = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("使用しない") },
                    { code: 1, name: nts.uk.resource.getText("使用する") }]);
            selectedDay: KnockoutObservable<number> = ko.observable(0);
            
            // H3_2
            optionStamping: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.GoingOutReason);
            selectedStamping: KnockoutObservable<number> = ko.observable(0);
            
            // H4_2
            simpleValue: KnockoutObservable<string> = ko.observable("");
            
            // H5_2
            letterColors: KnockoutObservable<string> = ko.observable('#FFCC00');
            
            // H6_2
            backgroundColors: KnockoutObservable<string> = ko.observable('#0000ff');
            
            // H7_2
            optionAudio: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.AudioType);
            selectedAudio: KnockoutObservable<number> = ko.observable(0);
        
            dataStampPage: KnockoutObservableArray<StampPageCommentCommand> = ko.observable(new StampPageCommentCommand({}));
            constructor() {
                let self = this;

            }
            /**
             * start page  
             */
            public startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                let dataStamp = nts.uk.ui.windows.getShared('KDP010_G');
                
                if (dataStamp) {
                        dataStamp = dataStamp.lstButtonSet ? dataStamp.lstButtonSet.filter(x=>x.buttonPositionNo == 1)[0] : dataStamp;
                        self.letterColors(dataStamp.buttonDisSet.buttonNameSet.textColor),
                        self.simpleValue(dataStamp.buttonDisSet.buttonNameSet.buttonName),
                        self.backgroundColors(dataStamp.buttonDisSet.backGroundColor),
                        self.selectedDay(dataStamp.buttonType.reservationArt),
                        self.selectedDay(dataStamp.buttonType.stampType.changeHalfDay),
                        self.selectedStamping(dataStamp.buttonType.stampType.goOutArt),
                        self.selectedDay(dataStamp.buttonType.stampType.setPreClockArt),
                        self.selectedDay(dataStamp.buttonType.stampType.changeClockArt),
                        self.selectedDay(dataStamp.buttonType.stampType.changeCalArt),
                        self.selectedHighlight(dataStamp.usrArt),
                        self.selectedAudio(dataStamp.audioType)
                }
                
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * Pass Data to A
             */
            public passData(): void {
                let self = this;
                
                self.dataStampPage = new StampPageCommentCommand({
                    buttonPositionNo: 1,
                    buttonDisSet: new ButtonDisSetCommand({
                        buttonNameSet: new ButtonNameSetCommand({
                            textColor : self.letterColors(),
                            buttonName : self.simpleValue()
                        }),
                        backGroundColor : self.backgroundColors()
                    }),
                    buttonType: new ButtonTypeCommand({
                        reservationArt : self.selectedDay(),
                        stampType : new StampTypeCommand ({
                            changeHalfDay : self.selectedDay(),
                            goOutArt : self.selectedStamping(),
                            setPreClockArt : self.selectedDay(),
                            changeClockArt : self.selectedDay(),
                            changeCalArt : self.selectedDay()
                        })
                    }),
                    usrArt: self.selectedHighlight(),
                    audioType: self.selectedAudio()
                    
                });
                
                nts.uk.ui.windows.setShared('KDP010_H', self.dataStampPage);
                nts.uk.ui.windows.close();
            }

            /**
             * Close dialog
             */
            public closeDialog(): void {
                let self = this;
                nts.uk.ui.windows.close();
            }
            
        }

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
        export class StampPageCommentCommand {
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

            constructor(param: IStampPageCommentCommand) {
                this.buttonPositionNo = param.buttonPositionNo;
                this.buttonDisSet = param.buttonDisSet;
                this.buttonType = param.buttonType;
                this.usrArt = param.usrArt;
                this.audioType = param.audioType;
            }
        }

        interface IStampPageCommentCommand {
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
}