module nts.uk.at.view.kmw003.a.service {
    var paths: any = {
        startScreen: "screen/at/monthlyperformance/startScreen",
        saveColumnWidth: "screen/at/correctionofdailyperformance/updatecolumnwidth",
        selectErrorCode: "screen/at/correctionofdailyperformance/errorCode",
        selectFormatCode: "screen/at/correctionofdailyperformance/selectCode",
        findCodeName: "screen/at/correctionofdailyperformance/findCodeName",
        findAllCodeName: "screen/at/correctionofdailyperformance/findAllCodeName",
        addAndUpdate: "screen/at/monthlyperformance/addAndUpdate",
        getApplication: "screen/at/correctionofdailyperformance/getApplication",
        updateScreen: "screen/at/monthlyperformance/updateScreen",
    }

    export function startScreen(param) {
        return nts.uk.request.ajax(paths.startScreen, param);
    }

    export function updateScreen(param) {
        return nts.uk.request.ajax(paths.updateScreen, param);
    }

    export function saveColumnWidth(param) {
        return nts.uk.request.ajax(paths.saveColumnWidth, param);
    }

    export function selectErrorCode(param) {
        return nts.uk.request.ajax(paths.selectErrorCode, param);
    }

    export function selectFormatCode(param) {
        return nts.uk.request.ajax(paths.selectFormatCode, param);

    }

    export function findCodeName(param) {
        return nts.uk.request.ajax(paths.findCodeName, param);

    }

    export function findAllCodeName(param) {
        return nts.uk.request.ajax(paths.findAllCodeName, param);

    }

    export function addAndUpdate(param) {
        return nts.uk.request.ajax(paths.addAndUpdate, param);

    }

    export function getApplication(param) {
        return nts.uk.request.ajax(paths.getApplication, param);

    }

}