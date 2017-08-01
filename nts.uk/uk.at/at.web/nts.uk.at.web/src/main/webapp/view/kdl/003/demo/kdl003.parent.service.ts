module kdl003.parent.service {
    var paths = {
        getPossibleWorkType: "at/share/worktype/getpossibleworktype"
    }
    /**
    * get all item (item co the duoc chon)
    */
    export function getItemSelected(lstPossible: Array<string>): JQueryPromise<Array<viewmodel.model.ItemModel>> {
        return nts.uk.request.ajax("at", paths.getPossibleWorkType, lstPossible);
    }
}