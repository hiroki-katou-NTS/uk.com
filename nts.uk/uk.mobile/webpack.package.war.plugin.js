const path = require('path'),
    ZipZip = require('zipzip');

function PackageWarFile() { }

PackageWarFile.prototype.apply = function (compiler) {
    compiler.hooks.done.tap('PackageWarFile', function () {
        // create ZipZip instance
        const zip = new ZipZip(path.join(__dirname, 'wwwroot', 'nts.uk.mobile.web.war'));

        // add a directory with different target
        zip.addDirectory(path.join(__dirname, 'wwwroot', 'nts.uk.mobile.web', 'dist'), '/dist');

        // add a custom file
        zip.addFile(path.join(__dirname, 'ClientApp', 'index.html'));
        //zip.addFile(path.join(__dirname, 'ClientApp', 'web.xml'), '/WEB-INF/web.xml');

        zip.build().then(() => console.log('War file has been created'));
    });
};

module.exports = PackageWarFile;