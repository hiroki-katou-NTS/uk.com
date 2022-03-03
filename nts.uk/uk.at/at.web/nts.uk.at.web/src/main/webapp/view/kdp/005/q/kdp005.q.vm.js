const config = { fps: 10, qrbox: 200 };
const html5QrCode = new Html5Qrcode("reader");
var objQRCode;
$(document).ready(function() {
	// Scanner
	function onScanSuccess(cardNumber) {
		if (cardNumber) {
			nts.uk.ui.windows.setShared("ICCardFromQRCode", cardNumber);
           	nts.uk.ui.windows.close();
		}
	}

	html5QrCode.start({ facingMode: "environment" }, config, onScanSuccess).then(function() {
		$('#render-action').show();
	});
	
	// Event click button clear
    $('#btn-clear').on('click', function() {
    	$('#render-action').hide();
        $('#output-qrcode-scanner').text('※ここに読み取り結果が表示されれます※');
        objQRCode = {};
        $('#btn-send').attr('disabled', 'disabled');

        // Stop, clear old scanner and start new scanner
        html5QrCode.stop().then(ignore => {
            html5QrCode.clear();
            html5QrCode.start({ facingMode: "environment" }, config, onScanSuccess).then(function() {
            	$('#render-action').show();
            });
        }).catch(err => {
            console.log(err);
        });
    })

});