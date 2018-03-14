module nts.uk.at.view.kmk011.d {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            saveAllSetting: "",
            findAllItemSetting:"at/record/divergence/time/companyDivergenceRefTime/find",
            deleteHist: "",
            findListHistory: "at/record/divergence/time/history/companyDivergenceRefTime/findAll",
            findUseUnitSetting: "",
            findListDivergenceTime: ""
        };
        
        export function save(data: model.ComDivergenceTimeSettingSaveCommand): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.saveAllSetting, data);
        }
        
        export function getAllItemSetting(historyId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAllItemSetting + "/" + historyId);
        }
        
        export function getAllHistory(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findListHistory);
        }
        
        export function deleteHistory(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.deleteHist)    
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
        }
        
        export class ComDivergenceTimeSettingSaveCommand {
            listComDivergenceTimeSetting: ComDivergenceTimeSettingDto[];
            
            constructor(listComDivergenceTimeSetting: ComDivergenceTimeSettingDto[]) {
                this.listComDivergenceTimeSetting = listComDivergenceTimeSetting;
            }
        }
        
        export class ComDivergenceTimeSettingDto {
            divergenceTimeNo: number;
            notUseAtr: number;
            historyId: string;
            alarmTime: number;
            errorTime: number;
            
            constructor(divergenceTimeNo: number, notUseAtr: number, historyId: string, alarmTime: number, errorTime: number){
                this.divergenceTimeNo = divergenceTimeNo;
                this.notUseAtr = notUseAtr;
                this.historyId = historyId;
                this.alarmTime = alarmTime;
                this.errorTime = errorTime;
            }
        }
    }
}