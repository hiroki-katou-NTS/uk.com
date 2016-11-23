/* global nts, _, UNKNOWN */

var services = (function () {

    var __employeeList = [
        {value: 'A000000000001', text: '日通　社員1'},
        {value: 'A000000000002', text: '日通　社員2'},
        {value: 'A000000000003', text: '日通　社員3'},
        {value: 'A000000000004', text: '日通　社員4'},
        {value: 'A000000000005', text: '日通　社員5'},
        {value: 'A000000000006', text: '日通　社員6'},
        {value: 'A000000000007', text: '日通　社員7'},
        {value: 'A000000000008', text: '日通　社員8'},
        {value: 'A000000000009', text: '日通　社員9'},
        {value: 'A0000000000010', text: '日通　社員10'}
    ];

    var servicePath = {
    	getEmployeeList: 'getEmployeeList',
    };

    nts.request.ajaxmock().set({
        req: function (path, options) {
            return path === servicePath.getEmployeeList;
        },
        res: function () {
            return {employeeList: __employeeList};
        }
    });

    var services = {};

    services.getEmployeeList = function () {
        var dfd = $.Deferred();

        nts.request.ajax(servicePath.getEmployeeList).done(function (res) {
            dfd.resolve(res);
        });

        return dfd.promise();
    };
   
    return services;
})();
