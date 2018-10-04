module nts.uk.at.view.kdp003.c {
    export module service {


        var servicePath = {
            findStampingOutput: "at/function/statement/findStampingOutput/",
            exportExcel: "at/function/statement/export",
            outputExcel: "screen/at/statement/export"
        }

        export function findStampOutput(stampCode: string): JQueryPromise<model.StampingOutputItemSetDto> {
            return nts.uk.request.ajax(servicePath.findStampingOutput + stampCode);

        }
        
        export function exportExcel(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.exportExcel, data);
        }
        
        export function outPutFileExcel(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(servicePath.outputExcel, data);
        }
        
         export module model {
            export interface StampingOutputItemSetDto {
                stampOutputSetCode: string;
                stampOutputSetName: string;
                outputEmbossMethod: boolean;
                outputWorkHours: boolean
                outputSetLocation: boolean;
                outputPosInfor: boolean;
                outputOT: boolean;
                outputNightTime: boolean;
                outputSupportCard: boolean;
            }
           

        }

    }
}

