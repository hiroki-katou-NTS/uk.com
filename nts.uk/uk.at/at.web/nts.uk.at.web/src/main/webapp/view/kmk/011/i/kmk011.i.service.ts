module nts.uk.at.view.kmk011.i {

    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getAllDivTime: "at/record/divergence/time/message/divergenceTimeErrAlarmMsg/getAllDivTime",
            save: "at/record/divergence/time/message/divergenceTimeErrAlarmMsg/save",
            findByDivergenceTimeNo: "at/record/divergence/time/message/divergenceTimeErrAlarmMsg/findByDivergenceTimeNo/"          
        };

        export function save(data: model.DivergenceTimeErrAlarmMsg): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.save, data);
        }
        
        export function getAllDivTime(): JQueryPromise<Array<model.DivergenceTimeErrAlarmMsg>>{
            return nts.uk.request.ajax("at", path.getAllDivTime); 
        }
        
        export function findByDivergenceTimeNo(divergenceTimeNo: number): JQueryPromise<model.DivergenceTimeErrAlarmMsg>{
            return nts.uk.request.ajax("at", path.findByDivergenceTimeNo + divergenceTimeNo);
        }
    }
    
    export module model{
        export class  DivergenceTimeErrAlarmMsg{
            divergenceTimeNo: number;
            divergenceTimeName: string;
            alarmMessage: string;
            errorMessage: string;
            
            constructor(divergenceTimeNo: number, divergenceTimeName: string, alarmMessage: string, errorMessage: string){
                this.divergenceTimeNo = divergenceTimeNo;
                this.divergenceTimeName = divergenceTimeName;
                this.alarmMessage = alarmMessage;
                this.errorMessage = errorMessage;
            }            
        }    
    }
}