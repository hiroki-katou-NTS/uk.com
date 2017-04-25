var cmm011;
(function (cmm011) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getCodeOfDepWP: "basic/organization/getcode",
                getAllDepartment: "basic/organization/getalldepartment",
                getAllDepartmentByHistId: "basic/organization/getalldepbyhistid/",
                getMemoByHistId: "basic/organization/getmemobyhistid/",
                getAllWorkPlace: "basic/organization/getallworkplace",
                getAllWorkPlaceByHistId: "basic/organization/getallwkpbyhistid/",
                getMemoWkpByHistId: "basic/organization/getmemowkpbyhistid/",
                addDepartment: "basic/organization/adddepartment",
                updateDepartment: "basic/organization/updatedepartment",
                updatelistDepartment: "basic/organization/updatedepartment",
                updateEndDateofWkp: "basic/organization/updateenddateofwkp",
                updateEndDateByHistId: "basic/organization/updateenddatewkpbyhistoryid",
                deleteHistory: "basic/organization/deletehistorywkp",
                updateStartDateandEndDate: "basic/organization/updatestartdateandenddatewkp",
                deleteDep: "basic/organization/deletedep",
                getAllWorkPLaceByHistId: "basic/organization/getallwkpbyhistid/",
                getMemoWorkPLaceByHistId: "basic/organization/getmemowkpbyhistid/",
                addWorkPlace: "basic/organization/addworkplace",
                updatelistWorkPLace: "basic/organization/updateworkplace",
            };
            function upDateListWorkplace(listworkplace) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updatelistWorkPLace, listworkplace).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.upDateListWorkplace = upDateListWorkplace;
            // add single WorkPlace
            function addWorkPlace(workplace) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addWorkPlace, workplace).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addWorkPlace = addWorkPlace;
            // add list WorkPlace
            function addListWorkPlace(listworkplace) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addWorkPlace, listworkplace).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addListWorkPlace = addListWorkPlace;
            /**
             * get list workplace theo historyID
             */
            function getAllWorkPLaceByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllWorkPLaceByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllWorkPLaceByHistId = getAllWorkPLaceByHistId;
            /**
             * get Memo theo historyID
             */
            function getMemoWorkPLaceByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getMemoWorkPLaceByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getMemoWorkPLaceByHistId = getMemoWorkPLaceByHistId;
            function deleteDepartment(department) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.deleteDep, department).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteDepartment = deleteDepartment;
            function upDateStartDateandEndDate(updateYMD) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateStartDateandEndDate, updateYMD).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.upDateStartDateandEndDate = upDateStartDateandEndDate;
            function deleteHistory(historyid) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.deleteHistory, historyid).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteHistory = deleteHistory;
            function updateEndDateByHistoryId(historyid) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateEndDateByHistId, historyid).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateEndDateByHistoryId = updateEndDateByHistoryId;
            function addDepartment(department) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addDepartment, department).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addDepartment = addDepartment;
            function upDateDepartment(department) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateDepartment, department).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.upDateDepartment = upDateDepartment;
            function upDateEndDateWkp(obj) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateEndDateofWkp, obj).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.upDateEndDateWkp = upDateEndDateWkp;
            function upDateListDepartment(listdepartment) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updatelistDepartment, listdepartment).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.upDateListDepartment = upDateListDepartment;
            function addListDepartment(listdepartment) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addDepartment, listdepartment).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addListDepartment = addListDepartment;
            function getCodeOfDepWP() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getCodeOfDepWP)
                    .done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getCodeOfDepWP = getCodeOfDepWP;
            /**
            * Get list dapartment tại listHistory[0].
            */
            function getAllDepartment() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllDepartment)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllDepartment = getAllDepartment;
            /**
             * get list dapartment theo historyID
             */
            function getAllDepartmentByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllDepartmentByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllDepartmentByHistId = getAllDepartmentByHistId;
            /**
             * get Memo theo historyID
             */
            function getMemoByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getMemoByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getMemoByHistId = getMemoByHistId;
            /**
           * Get list dapartment tại listHistory[0].
           */
            function getAllWorkplace() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllWorkPlace)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllWorkplace = getAllWorkplace;
            /**
            * get list dapartment theo historyID
            */
            function getAllWorkPlaceByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllWorkPlaceByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllWorkPlaceByHistId = getAllWorkPlaceByHistId;
            /*
            * get Memo theo historyID
            */
            function getMemoWkpByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getMemoWkpByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getMemoWkpByHistId = getMemoWkpByHistId;
        })(service = a.service || (a.service = {}));
    })(a = cmm011.a || (cmm011.a = {}));
})(cmm011 || (cmm011 = {}));
