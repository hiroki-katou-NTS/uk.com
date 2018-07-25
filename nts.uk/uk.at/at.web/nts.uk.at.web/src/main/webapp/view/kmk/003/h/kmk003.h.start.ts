module nts.uk.at.view.kmk003.h {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            let container = $("#component-uk");
            container.focus();
            container.keyup(function(evt, ui) {
                if (container.data("enable") !== false) {
                    let code = evt.which || evt.keyCode;
                    let checkitem;
                    if (code === 32) {
//                        checkitem = $(_.find(container.find("input[type='radio']"), function(radio, idx) {
//                            return $(radio).attr("disabled") !== "disabled";
//                        }));
                    } else if (code === 37 || code === 38) {
                        let inputList = _.filter(container.find("input[type='radio']"), function(radio, idx) {
                            return $(radio).attr("disabled") !== "disabled";
                        });
                        //                        let inputList = container.find("input[type='radio']");
                        let currentSelect = _.findIndex(inputList, function(item) {
                            return $(item).is(":checked");
                        });
                        checkitem = $(inputList[currentSelect - 1]);
                    } else if (code === 39 || code === 40) {
                        let inputList = _.filter(container.find("input[type='radio']"), function(radio, idx) {
                            return $(radio).attr("disabled") !== "disabled";
                        });
                        let currentSelect = _.findIndex(inputList, function(item) {
                            return $(item).is(":checked");
                        });
                        checkitem = $(inputList[currentSelect + 1])
                    }
                    if (checkitem !== undefined && checkitem.length > 0) {
                        checkitem.prop("checked", true);
                        let inputList = _.filter(container.find("input[type='radio']"), function(radio, idx) {
                            return $(radio).attr("disabled") !== "disabled";
                        });
                        let currentSelect = _.findIndex(inputList, function(item) {
                            return $(item).is(":checked");
                        });
                        screenModel.calcMethodFixed(currentSelect);
                    }
                }
            });
            _.defer(() => screenModel.setInitialFocus());
        });
    });
}