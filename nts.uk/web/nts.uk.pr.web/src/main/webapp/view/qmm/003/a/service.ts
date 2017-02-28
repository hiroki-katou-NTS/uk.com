module qmm003.a.service {
    var paths = {
        getResidentalTaxList: "pr/core/residential/findallresidential"
    }
    
    /**
     * Get list payment date processing.
     */
    export function getResidentalTax(): JQueryPromise<Array<model.ResidentialTax>> {
        var dfd = $.Deferred<Array<qmm003.a.service.model.ResidentialTax>>();
        nts.uk.request.ajax(paths.getResidentalTaxList)
            .done(function(res:  Array<qmm003.a.service.model.ResidentialTax>){
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise(); 
    }
    export module model{
      export class ResidentialTax{
          companyCode: string;
          resiTaxCode: string;
          resiTaxAutonomy: string;
          prefectureCode: string;
          resiTaxReportCode: string;
          registeredName: string;
          companyAccountNo: string;
          companySpecifiedNo: string;
          cordinatePostalCode: string;
          cordinatePostOffice: string;
          memo: string;
          contructor(companyCode: string, resiTaxCode: string,resiTaxAutonomy: string,
           prefectureCode: string,resiTaxReportCode: string, 
           registeredName: string, companyAccountNo: string, companySpecifiedNo: string,
           cordinatePostalCode: string, cordinatePostOffice: string,memo: string){
              this.companyCode = companyCode;
              this.resiTaxCode = resiTaxCode;
              this.resiTaxAutonomy= resiTaxAutonomy;
              this.prefectureCode = prefectureCode;
              this.resiTaxReportCode = resiTaxReportCode;
              this.registeredName = registeredName;
              this.companyAccountNo = companyAccountNo;
              this.companySpecifiedNo = companySpecifiedNo;
              this.cordinatePostalCode = cordinatePostalCode;
              this.cordinatePostOffice = cordinatePostOffice;
              this.memo = memo;
              }
      }
        
    }

}