module nts.uk.at.view.kcp002.a {

    export module service {
        var paths: any = {
            findAllClassification: "basic/company/organization/classification/findAll"
        };

        //connection service find
        export function findAllClassification(): JQueryPromise<model.ClassificationFindDto> {
            //call service server
            return nts.uk.request.ajax("com", paths.findAllClassification);
        }
        
        export module model {
            
            export class ClassificationFindDto {
                code: string;
                name: string;
            }
            
        }

    }

}