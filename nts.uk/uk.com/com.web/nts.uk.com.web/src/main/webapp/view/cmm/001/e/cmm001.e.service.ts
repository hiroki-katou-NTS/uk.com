module cmm001.e.service {
    var paths = {
        getAllMasterCopyCategory : "sys/assist/mastercopy/category/getAllMasterCopyCategory",
    }
    
    export function getAllMasterCopyCategory() {
        return nts.uk.request.ajax(paths.getAllMasterCopyCategory);    
    }
}