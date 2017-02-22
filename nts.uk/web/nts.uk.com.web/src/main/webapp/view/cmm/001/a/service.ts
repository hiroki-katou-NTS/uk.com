module cmm001.a.service {
        var paths = {
            getAllCompanys: "ctx/proto/company/findallcompany",
            getCompanyDetail: "ctx/proto/company/find/{companyCode}",
            deleteCompany: "ctx/proto/company/deletedata",
            updateCompany: "ctx/proto/company/updatedata",
            addCompany: "ctx/proto/company/adddata"
        }
        /**
         * get list company
         */
        export function getAllCompanys(): JQueryPromise<Array<model.CompanyDto>>{
            let dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getAllCompanys)
                  .done(function(res: Array<any>){
                      dfd.resolve(res);
                      })
                  .fail(function(res){
                      dfd.reject(res);
                      })
             return dfd.promise();
            
            }
        /**
         * get a company 
         */
        export function getCompanyDetail(): JQueryPromise<Array<model.CompanyDto>>{
            let dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getCompanyDetail)
                  .done(function(res: Array<any>){
                      dfd.resolve(res);
                  })
                  .fail(function(res){
                      dfd.reject(res);
                      
                  })
            return dfd.promise();
            
        }
        export function addData(company: model.CompanyDto){
        var dfd = $.Deferred<Array<any>>();  
        nts.uk.request.ajax(paths.addCompany, company).done(function(res: Array<any>){
            dfd.resolve(res);    
        }).fail(function(res){
            dfd.reject(res);
        })
        return dfd.promise(); 
    }
        export function updateData(company: model.CompanyDto): JQueryPromise<Array<model.CompanyDto>>{
           let dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.updateCompany,company)
                    .done(function(res: Array<any>){
                        dfd.resolve(res);
                    })
                    .fail(function(res){
                        dfd.reject(res);
                    })
            return dfd.promise();
        }
        export module model {
                // company
                export class CompanyDto {
                    companyCode: string;
                    companyName: string;
                    companyNameGlobal: string;
                    address1: string;
                    address2: string;
                    addressKana1: string;
                    addressKana2: string;
                    companyNameAbb: string;
                    companyNameKana: string;
                    corporateMyNumber: string;
                    depWorkPlaceSet: number;
                    displayAttribute: number; // cot thu 3
                    faxNo: string;
                    postal: string;
                    presidentName: string;
                    presidentJobTitle: string;
                    telephoneNo: string;
                    termBeginMon: number;   
                    use_Gr_Set: number;
                    use_Kt_Set: number;
                    use_Qy_Set: number;
                    use_Jj_Set: number;
                    use_Ac_Set: number;
                    use_Gw_Set: number;
                    use_Hc_Set: number;
                    use_Lc_Set: number;
                    use_Bi_Set: number;
                    use_Rs01_Set: number;
                    use_Rs02_Set: number;
                    use_Rs03_Set: number;
                    use_Rs04_Set: number;
                    use_Rs05_Set: number;
                    use_Rs06_Set: number;
                    use_Rs07_Set: number;
                    use_Rs08_Set: number;
                    use_Rs10_Set: number;
                }
    
            }

}