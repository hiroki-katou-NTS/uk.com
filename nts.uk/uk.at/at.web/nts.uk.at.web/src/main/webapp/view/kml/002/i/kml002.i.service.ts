module kml002.i.service {
    var paths: any = {
        addVerticalTime: "ctx/at/schedule/budget/fixedverticalsetting/addVerticalTime",
        updateVerticalTime: "ctx/at/schedule/budget/fixedverticalsetting/updateVerticalTime",
        deleteVerticalTime: "ctx/at/schedule/budget/fixedverticalsetting/deleteVerticalTime",
        findVerticalSet: "ctx/at/schedule/budget/fixedverticalsetting/find/"
    }
    export function findVerticalSet(fixedItemAtr): JQueryPromise<Array<viewmodel.IVerticalTime>> {
        return nts.uk.request.ajax("at",paths.findVerticalSet + fixedItemAtr);
    }
    export function addVerticalTime(vertical: any) {
        return nts.uk.request.ajax("at", paths.addVerticalTime, vertical);
    }
    export function updateVerticalTime(vertical: any) {
        return nts.uk.request.ajax("at",paths.updateVerticalTime, vertical);
    }
    export function deleteVerticalTime(vertical: any) {
        return nts.uk.request.ajax("at",paths.deleteVerticalTime, vertical);
    }
}