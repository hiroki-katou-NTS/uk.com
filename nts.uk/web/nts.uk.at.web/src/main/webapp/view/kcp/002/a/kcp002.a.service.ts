module nts.uk.at.view.kcp002.a {

    export module service {
        var paths: any = {
            findAllManagementCategory: "basic/company/organization/management/category/findAll"
        };

        //connection service find
        export function findAllManagementCategory(): JQueryPromise<model.ManagementCategoryFindDto> {
            //call service server
            return nts.uk.request.ajax("com", paths.findAllManagementCategory);
        }
        
        export module model {
            
            export class ManagementCategoryFindDto {
                code: string;
                name: string;
            }
            
        }

    }

}