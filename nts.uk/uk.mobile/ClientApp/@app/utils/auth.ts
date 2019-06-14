import { storage } from '@app/utils';

export const auth = {
    get valid(): boolean {
        return !!storage.cookie.hasItem('sescon');
    },
    get token(): string | null {
        return storage.local.getItem('csrf') as string;
    },
    get user() {
        let user: any = storage.local.getItem('user'),
            role: any = (user || { role: {} }).role || {};

        return user && {
            employee: user.employee,
            companyId: user.companyId,
            employeeId: user.employeeId,
            employeeCode: user.employeeCode,
            constractCode: user.constractCode,
            role: {
                payroll: role.payroll,
                personnel: role.personnel,
                attendance: role.attendance,
                systemAdmin: role.systemAdmin,
                companyAdmin: role.companyAdmin,
                personalInfo: role.personalInfo,
                officeHelper: role.officeHelper,
                groupCompanyAdmin: role.groupCompanyAdmin
            }
        };
    }
};