module nts.uk.at.view.kdp005.h2 {

	const config = { fps: 10, qrbox: 200 };
	const html5QrCode = new Html5Qrcode("reader");

	export module viewmodel {
		var constraints = {
			video: true,
			audio: true
		}

		$(document).ready(function() {
			// Scanner
			function onScanSuccess(cardNumber: any) {
				if (cardNumber) {
					nts.uk.ui.windows.setShared("ICCardFromQRCode", cardNumber);
					nts.uk.ui.windows.close();
				}
			}

			html5QrCode.start({ facingMode: "environment" }, config, onScanSuccess).then(function() {
				navigator.mediaDevices.getUserMedia(constraints).then(function success() {
					$('#render-action').show();
				});

			}).catch(function(err: any) {
				console.log(err);
				if (err) {
					$('#lbl-error').removeAttr('display');
					$('#btn-cancel').css('bottom', '-260px');
					$('#btn-clear').css('visibility', 'hidden');
					
				}
			});

			// Event click button clear
			$('#btn-clear').on('click', function() {
				$('#render-action').hide();
				$('#output-qrcode-scanner').text('※ここに読み取り結果が表示されれます※');

				// Stop, clear old scanner and start new scanner
				html5QrCode.stop().then(() => {
					html5QrCode.clear();
					html5QrCode.start({ facingMode: "environment" }, config, onScanSuccess).then(function() {
						$('#render-action').show();
					});
				}).catch((err: any) => {
					console.log(err);
				});
			})

			$('#btn-cancel').on('click', function() {
				nts.uk.ui.windows.close();
			});

		});

	}
}