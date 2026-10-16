// Workaround for Webpack 5 + Karma ChromeHeadless in Wasm browser tests:
// Direct access to `import.meta` in dependencies (e.g. km-io / @JsFun) is left as raw `import.meta`
// by Webpack when bundling classic scripts for Karma, causing Chrome to fail at parse time.
// DefinePlugin safely rewrites `import.meta` to an object during Webpack test bundling.
const webpack = require('webpack');
config.plugins = config.plugins || [];
config.plugins.push(new webpack.DefinePlugin({
    'import.meta': JSON.stringify({ url: '' })
}));
