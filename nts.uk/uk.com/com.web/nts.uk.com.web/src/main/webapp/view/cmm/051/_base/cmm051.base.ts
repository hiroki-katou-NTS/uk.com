module cmm051 {
    export module base {
        
        export interface IWorkplaceManager {
            workplaceId?: string;
            employeeId: string;
            startDate: string;
            endDate: string;
            roleId?: string;
        }
    }
}