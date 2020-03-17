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
            contentsStampType: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ContentsStampType);
            selectedDay: KnockoutObservable<number> = ko.observable(1);
            
            // H3_2
            optionStamping: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.GoingOutReason);
            selectedStamping: KnockoutObservable<number> = ko.observable(0);
            
            // H4_2
            simpleValue: KnockoutObservable<string> = ko.observable("");
            
            // H5_2
            letterColors: KnockoutObservable<string> = ko.observable('#0033cc');
            
            // H6_2
            backgroundColors: KnockoutObservable<string> = ko.observable('#ccccff');
            
            // H7_2
            optionAudio: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.AudioType);
            selectedAudio: KnockoutObservable<number> = ko.observable(0);
        
            dataStampPage: KnockoutObservableArray<StampPageCommentCommand> = ko.observable(new StampPageCommentCommand({}));
           
            lstChangeClock: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ChangeClockArt);
            lstChangeCalArt: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ChangeCalArt);
            lstContents: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ContentsStampType);
            
            lstData: KnockoutObservableArray<any> = ko.observableArray();
            constructor() {
                let self = this;
                self.selectedDay.subscribe((newValue) => {
                    self.getDataFromContents(newValue);
                })
            }
            /**
             * start page  
             */
            public startPage(): JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();
                self.getDataStamp();
                dfd.resolve();
                return dfd.promise();
            }
            
            public getDataStamp(){
                
                let self = this;
                let dataStamp = nts.uk.ui.windows.getShared('KDP010_G');
                if (dataStamp) {
                        dataStamp = dataStamp.lstButtonSet ? dataStamp.lstButtonSet.filter(x=>x.buttonPositionNo == 1)[0] : dataStamp;
                        self.letterColors(dataStamp.buttonDisSet.buttonNameSet.textColor),
                        self.simpleValue(dataStamp.buttonDisSet.buttonNameSet.buttonName),
                        self.backgroundColors(dataStamp.buttonDisSet.backGroundColor),
                        self.selectedDay(2),
                        self.selectedStamping(dataStamp.buttonType.stampType.goOutArt),
                        self.selectedHighlight(dataStamp.usrArt),
                        self.selectedAudio(dataStamp.audioType)
                }
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
                        reservationArt : self.lstData.reservationArt,
                        stampType : new StampTypeCommand ({
                            changeHalfDay : self.lstData.changeHalfDay,
                            goOutArt : self.selectedStamping(),
                            setPreClockArt : self.lstData.setPreClockArt,
                            changeClockArt : self.lstData.changeClockArt,
                            changeCalArt : self.lstData.changeCalArt
                        })
                    }),
                    usrArt: self.selectedHighlight(),
                    audioType: self.selectedAudio()
                    
                });
                
                nts.uk.ui.windows.setShared('KDP010_H', self.dataStampPage);
                nts.uk.ui.windows.close();
            }
            
            public getDataFromContents(number : value): void{
                let self = this;
                self.lstData = {
                    reservationArt: 1,
                    changeHalfDay: 1,
                    setPreClockArt: 1,
                    changeClockArt: 1,
                    changeCalArt: 1
                };
                switch (self.selectedDay()) {
                    case 1:
                        self.lstData = { changeClockArt: self.lstChangeClock()[0].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 2:
                        self.lstData = { changeClockArt: self.lstChangeClock()[0].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 3:
                        self.lstData = { changeClockArt: self.lstChangeClock()[0].value, changeCalArt: self.lstChangeCalArt()[1].value, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 4:
                        self.lstData = { changeClockArt: self.lstChangeClock()[0].value, changeCalArt: self.lstChangeCalArt()[3].value, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 5:
                        self.lstData = { changeClockArt: self.lstChangeClock()[1].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 6:
                        self.lstData = { changeClockArt: self.lstChangeClock()[1].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 7:
                        self.lstData = { changeClockArt: self.lstChangeClock()[1].value, changeCalArt: self.lstChangeCalArt()[2].value, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 8:
                        self.lstData = { changeClockArt: self.lstChangeClock()[7].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 9:
                        self.lstData = { changeClockArt: self.lstChangeClock()[8].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 10:
                        self.lstData = { changeClockArt: self.lstChangeClock()[2].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 11:
                        self.lstData = { changeClockArt: self.lstChangeClock()[3].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 12:
                        self.lstData = { changeClockArt: self.lstChangeClock()[10].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 13:
                        self.lstData = { changeClockArt: self.lstChangeClock()[11].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 14:
                        self.lstData = { changeClockArt: self.lstChangeClock()[4].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 15:
                        self.lstData = { changeClockArt: self.lstChangeClock()[5].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 16:
                        self.lstData = { changeClockArt: self.lstChangeClock()[9].value, changeCalArt: null, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 17:
                        self.lstData = { changeClockArt: self.lstChangeClock()[4].value, changeCalArt: self.lstChangeCalArt()[2].value, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 18:
                        self.lstData = { changeClockArt: self.lstChangeClock()[4].value, changeCalArt: self.lstChangeCalArt()[3].value, setPreClockArt: null, changeHalfDay: 0,reservationArt: null};
                        break;
                    case 19:
                        self.lstData = { changeClockArt: null, changeCalArt: null, changeCalArt: null, changeHalfDay: 0,reservationArt: 1};
                        break;
                    case 20:
                        self.lstData = { changeClockArt: null, changeCalArt: null, changeCalArt: null, changeHalfDay: 0,reservationArt: 2};
                        break;
                }
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