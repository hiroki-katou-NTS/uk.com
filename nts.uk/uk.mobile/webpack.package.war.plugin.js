const path = require('path'),
    ZipZip = require('zipzip');

function PackageWarFile(env) {
    this.env = env || {};
}

PackageWarFile.prototype.apply = function (compiler) {
    let self = this;
    
    compiler.hooks.done.tap('PackageWarFile', function () {
        if (self.env.prod) {
            // create ZipZip instance
            const zip = new ZipZip(path.join(__dirname, 'wwwroot', 'nts.uk.mobile.web.war'));

            // add a directory with different target
            zip.addDirectory(path.join(__dirname, 'wwwroot', 'nts.uk.mobile.web', 'dist'), '/dist');

            // add a custom file
            zip.addFile(path.join(__dirname, 'ClientApp', 'index.html'));
            zip.addFile(path.join(__dirname, 'ClientApp', 'web.xml'), '/WEB-INF/web.xml');

            zip.build().then(() => {
                console.log('\n');
                console.log('   +--------------------------------------------------------------+');
                console.log('   | War file [nts.uk.mobile.web.war] has been created at wwwroot |');
                console.log('   +--------------------------------------------------------------+');
                console.log('\n');
            });
        }
    });
};

module.exports = PackageWarFile;