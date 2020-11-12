module cmm044.c.service {
    const paths: any = {
        sendMail: "workflow/agent/sendMail",
    };

    export function sendMail(data: any) {
        return nts.uk.request.ajax("com", paths.sendMail, data)
    }
}