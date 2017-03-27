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
    export function getAllCompanys(): JQueryPromise<Array<any>> {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getAllCompanys)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    /**
     * get a company 
     */
    export function getCompanyDetail(): JQueryPromise<Array<model.CompanyDto>> {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getCompanyDetail)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);

            })
        return dfd.promise();

    }
    export function addData(company: model.CompanyDto) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.addCompany, company).done(function(res: Array<any>) {
            dfd.resolve(res);
        }).fail(function(res) {
            dfd.reject(res);
        })
        return dfd.promise();
    }

    export function updateData(company: model.CompanyDto) {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.updateCompany, company)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
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
            termBeginMon: number =0;
            use_Gr_Set: number =0;
            use_Kt_Set: number =0;
            use_Qy_Set: number =0;
            use_Jj_Set: number =0;
            use_Ac_Set: number =0;
            use_Gw_Set: number =0;
            use_Hc_Set: number =0 ;
            use_Lc_Set: number =0;
            use_Bi_Set: number =0;
            use_Rs01_Set: number =0;
            use_Rs02_Set: number =0;
            use_Rs03_Set: number =0;
            use_Rs04_Set: number =0;
            use_Rs05_Set: number =0;
            use_Rs06_Set: number =0;
            use_Rs07_Set: number =0;
            use_Rs08_Set: number =0;
            use_Rs09_Set: number =0;
            use_Rs10_Set: number =0;
            constructor(companyCode: string, companyName: string, companyNameGlobal: string,
                address1: string, address2: string, addressKana1: string, addressKana2: string,companyNameAbb: string,
                companyNameKana: string, corporateMyNumber: string, depWorkPlaceSet: number, displayAttribute: number, // cot thu 3
                faxNo: string, postal: string, presidentName: string, presidentJobTitle: string, telephoneNo: string,
                termBeginMon: number, useGrSet: number, useKtSet: number, useQySet: number, useJjSet: number, useAcSet: number, useGwSet: number,
                useHcSet: number, useLcSet: number, useBiSet: number, useRs01Set: number, useRs02Set: number, useRs03Set: number, useRs04Set: number,
                useRs05Set: number, useRs06Set: number, useRs07Set: number, useRs08Set: number, useRs09Set: number, useRs10Set: number) {
                this.companyCode= companyCode;
                this.companyName = companyName;
                this.companyNameGlobal = companyNameGlobal;
                this.companyNameAbb = companyNameAbb;
                this.companyNameKana = companyNameKana;
                this.address1 = (address1);
                this.address2 = (address2);
                this.addressKana1 = (addressKana1);
                this.addressKana2 = (addressKana2);
                this.use_Gr_Set = useGrSet;
                this.use_Kt_Set = useKtSet;
                this.use_Qy_Set = useQySet;
                this.use_Jj_Set = useJjSet;
                this.use_Ac_Set = useAcSet;
                this.use_Gw_Set = useGwSet;
                this.use_Hc_Set = useHcSet;
                this.use_Lc_Set = useLcSet;
                this.use_Bi_Set = useBiSet;
                this.use_Rs01_Set = useRs01Set;
                this.use_Rs02_Set = useRs02Set;
                this.use_Rs03_Set = useRs03Set;
                this.use_Rs04_Set = useRs04Set;
                this.use_Rs05_Set = useRs05Set;
                this.use_Rs06_Set = useRs06Set;
                this.use_Rs07_Set = useRs07Set;
                this.use_Rs08_Set = useRs08Set;
                this.use_Rs09_Set = useRs09Set;
                this.use_Rs10_Set = useRs10Set;

            }
        }

    }
}