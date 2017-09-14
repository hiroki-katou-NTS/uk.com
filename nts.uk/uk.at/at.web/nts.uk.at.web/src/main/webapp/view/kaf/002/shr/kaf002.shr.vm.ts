module nts.uk.at.view.kaf002.shr {
    export module vmbase {
        export class Application {
            applicationID: KnockoutObservable<string>; // 申請ID
            prePostAtr: KnockoutObservable<number>; // 事前事後区分
            inputDate: KnockoutObservable<string>; // 入力日
            enteredPerson: KnockoutObservable<string>; // 入力者
            applicationDate: KnockoutObservable<string>; // 申請日
            applicationReason: KnockoutObservable<string>; // 申請理由
            applicationType: KnockoutObservable<number>; // 申請種類
            employeeID: KnockoutObservable<string>; // 申請者
            constructor( 
                applicationID: string,
                prePostAtr: number,
                inputDate: string,
                enteredPerson: string,
                applicationDate: string,
                applicationReason: string,
                applicationType: number,
                employeeID: string){
                    this.applicationID = ko.observable(applicationID);  
                    this.prePostAtr = ko.observable(prePostAtr);
                    this.inputDate = ko.observable(inputDate);
                    this.enteredPerson = ko.observable(enteredPerson);
                    this.applicationDate = ko.observable(applicationDate);
                    this.applicationReason = ko.observable(applicationReason);
                    this.applicationType = ko.observable(applicationType);
                    this.employeeID = ko.observable(employeeID);
            }
        }
        
        export class AppStampGoOutPermit {
            stampAtr: KnockoutObservable<number>;
            stampFrameNo: KnockoutObservable<number>;
            stampGoOutReason: KnockoutObservable<number>;
            startTime: KnockoutObservable<number>;
            startLocation: KnockoutObservable<string>;
            endTime: KnockoutObservable<number>;
            endLocation: KnockoutObservable<string>;    
            constructor(
                stampAtr: number,
                stampFrameNo: number,
                stampGoOutReason: number,
                startTime: number,
                startLocation: string,
                endTime: number,
                endLocation: string){
                    this.stampAtr = ko.observable(stampAtr);
                    this.stampFrameNo = ko.observable(stampFrameNo);
                    this.stampGoOutReason = ko.observable(stampGoOutReason);
                    this.startTime = ko.observable(startTime);
                    this.startLocation = ko.observable(startLocation);
                    this.endTime = ko.observable(endTime);
                    this.endLocation = ko.observable(endLocation);
            }
        }
        
        export class AppStampWork {
            stampAtr: KnockoutObservable<number>;
            stampFrameNo: KnockoutObservable<number>;
            stampGoOutReason: KnockoutObservable<number>;
            supportCard: KnockoutObservable<string>;
            supportLocationCD: KnockoutObservable<string>;
            startTime: KnockoutObservable<number>;
            startLocation: KnockoutObservable<string>;
            endTime: KnockoutObservable<number>;
            endLocation: KnockoutObservable<string>;   
            constructor(
                stampAtr: number,
                stampFrameNo: number,
                stampGoOutReason: number,
                supportCard: string,
                supportLocationCD: string,
                startTime: number,
                startLocation: string,
                endTime: number,
                endLocation: string){
                    this.stampAtr = ko.observable(stampAtr);
                    this.stampFrameNo = ko.observable(stampFrameNo);
                    this.stampGoOutReason = ko.observable(stampGoOutReason);
                    this.supportCard = ko.observable(supportCard);
                    this.supportLocationCD = ko.observable(supportLocationCD);
                    this.startTime = ko.observable(startTime);
                    this.startLocation = ko.observable(startLocation);
                    this.endTime = ko.observable(endTime);
                    this.endLocation = ko.observable(endLocation);
            }
        }
        
        export class AppStampCancel {
            stampAtr: KnockoutObservable<number>;
            stampFrameNo: KnockoutObservable<number>;
            cancelAtr: KnockoutObservable<number>;
            constructor(stampAtr: number, stampFrameNo: number, cancelAtr: number){
                this.stampAtr = ko.observable(stampAtr);
                this.stampFrameNo = ko.observable(stampFrameNo);
                this.cancelAtr = ko.observable(cancelAtr);         
            }
        }
        
        export class AppStampOnlineRecord {
            stampCombinationAtr: KnockoutObservable<number>;
            appTime: KnockoutObservable<number>;    
            constructor(stampCombinationAtr: number, appTime: number){
                this.stampCombinationAtr = ko.observable(stampCombinationAtr);
                this.appTime = ko.observable(appTime);        
            }
        }
    }
}