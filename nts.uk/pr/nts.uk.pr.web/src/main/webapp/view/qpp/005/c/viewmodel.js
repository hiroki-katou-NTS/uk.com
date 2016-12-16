/* global services, nts, _, ko, form */

function ScreenModel() {
	var self = this;
	self.employeeList = ko.observable(new EmployeeList());
}
ScreenModel.prototype.start = function() {
	var self = this;

//	services.getEmployeeList().done(function(res) {
//		var employeeList = _.map(service.employeeList, function(employee) {
//			return new Employee(employee.code, employee.name);
//		});
//		self.employees().items(employeeList);
//	});

	 var employeeMocks = [
		 {personId: 'A00000000000000000000000000000000001', code: 'A000000000001', name: '日通　社員1' },
         {personId: 'A00000000000000000000000000000000002', code: 'A000000000002', name: '日通　社員2' },
         {personId: 'A00000000000000000000000000000000003', code: 'A000000000003', name: '日通　社員3' },
         {personId: 'A00000000000000000000000000000000004', code: 'A000000000004', name: '日通　社員4' },
         {personId: 'A00000000000000000000000000000000005', code: 'A000000000005', name: '日通　社員5' },
         {personId: 'A00000000000000000000000000000000006', code: 'A000000000006', name: '日通　社員6' },
         {personId: 'A00000000000000000000000000000000007', code: 'A000000000007', name: '日通　社員7' },
         {personId: 'A00000000000000000000000000000000008', code: 'A000000000008', name: '日通　社員8' },
         {personId: 'A00000000000000000000000000000000009', code: 'A000000000009', name: '日通　社員9' },
         {personId: 'A00000000000000000000000000000000010', code: 'A000000000010', name: '日通　社員10' }
	    ];
	var employeeList = _.map(employeeMocks, function(employee) {
		return new Employee(employee.personId, employee.code, employee.name);
	});
	
	self.employeeList().items(employeeList);
};

ScreenModel.prototype.chooseEmployee = function() {
	var self = this;
	nts.uk.ui.windows.setShared('employee', self.employeeList().selected());
	nts.uk.ui.windows.close();
}

ScreenModel.prototype.close = function() {
	nts.uk.ui.windows.close();
}

function EmployeeList() {
	var self = this;

	self.items = ko.observableArray([]);
	self.selected = ko.observable();
}
function Employee(personId, code, name) {
	var self = this;
	
	self.personId = personId;
	self.code = code;
	self.name = name;
}
