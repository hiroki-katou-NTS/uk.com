module nts.uk.at.view.kmk011.d {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            saveAllSetting: "",
            findAllItemSetting:"at/record/divergence/time/companyDivergenceRefTime/find",
            deleteHist: "at/record/divergence/time/history/companyDivergenceRefTime/remove",
            findListHistory: "at/record/divergence/time/history/companyDivergenceRefTime/findAll",
            findUseUnitSetting: "at/record/divergence/time/history/divergenceRefTimeUsageUnit/find",
            findListDivergenceTime: "at/record/divergencetime/setting/getalldivtime"
        };
        
        export function save(data: model.ComDivergenceTimeSettingSaveCommand): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.saveAllSetting, data);
        }
        
        export function getAllItemSetting(historyId: string): JQueryPromise<any> {
            if (nts.uk.text.isNullOrEmpty(historyId)){
                    historyId = "0";
            }
            return nts.uk.request.ajax("at", path.findAllItemSetting + "/" + historyId);
        }
        
        export function getAllHistory(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findListHistory);
        }
        
        export function deleteHistory(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.deleteHist, command)    
        }
        
        export function getUseUnitSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findUseUnitSetting);
        }
        
        export function getAllDivergenceTime(): JQueryPromise<model.DivergenceTimeDto[]>{
            return nts.uk.request.ajax("at", path.findListDivergenceTime);
        }
    }
    
    export module model {
        export class CompanyDivergenceReferenceTimeHistoryDto {
            historyId: string;
            startDate: string;
            endDate: string;
        }
        
        export class DivergenceTimeDto {
            divergenceTimeNo: number;
            divergenceTimeName: string;
            divergenceTimeUseSet:number;
            
            constructor(divergenceTimeNo: number, divergenceTimeName: string, divergenceTimeUseSet: number){
                this.divergenceTimeNo = divergenceTimeNo
                this.divergenceTimeName = divergenceTimeName
                this.divergenceTimeUseSet = divergenceTimeUseSet
            }
            
        }
        
        export class ComDivergenceTimeSettingSaveCommand {
            listComDivergenceTimeSetting: ComDivergenceTimeSettingDto[];
            
            constructor(listComDivergenceTimeSetting: ComDivergenceTimeSettingDto[]) {
                this.listComDivergenceTimeSetting = listComDivergenceTimeSetting;
            }
        }
        
        export class ComDivergenceTimeSettingDto {
            divergenceTimeNo: KnockoutObservable<number>;
            notUseAtr: KnockoutObservable<number>;
            historyId: KnockoutObservable<string>;
            alarmTime: KnockoutObservable<number>;
            errorTime: KnockoutObservable<number>;
            
            constructor(divergenceTimeNo: number, notUseAtr: number, historyId: string, alarmTime: number, errorTime: number){
                this.divergenceTimeNo = ko.observable(divergenceTimeNo);
                this.notUseAtr = ko.observable(notUseAtr);
                this.historyId = ko.observable(historyId);
                this.alarmTime = ko.observable(alarmTime);
                this.errorTime = ko.observable(errorTime);
            }
        }
    }
}