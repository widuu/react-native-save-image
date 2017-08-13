
# react-native-save-image

> react-native `Android`版 保存网络图片到相册

## 安装

> 快速安装

`$ npm install react-native-save-image --save`

`$ react-native link react-native-save-image` or `rnpm link react-native-save-image`

> 手动配置

1. 修改 `android/settings.gradle`，添加如下的代码：

```
    	include ':react-native-save-image'   
	project(':react-native-save-image').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-save-image/android')
```

2. 修改 `android/app/build.gradle file`

```
	...
	dependencies {
	    ...
	    compile project(':react-native-splash-screen')
	}
```

3. 修改 `MainApplication.java `

```
    import com.widuu.SaveImagePackage; // 顶部导入

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new SaveImagePackage(), // 这里添加
      );
    }
```

## 使用

```
	import SaveImage from 'react-native-save-image';

	export default class SaveImage extends React.Component {

	    componentDidMount() {
	    	SaveImage.setAlbumName('相册文件夹名称');
    		SaveImage.setCompressQuality(80); // 整数品质
	    }

	    render(){
	    	return(
		    <View style={styles.container}>
		        <TouchableHighlight onLongPress={()=>SaveImage.downloadImage(图片地址)}>
		 	    <Image
			        source={{uri:图片地址}}
			        style={styles.images}
			        resizeMode='contain'
			     />
              		</TouchableHighlight>
            	    </View>
	    	);
	    }
	}
```

## API

    SaveImage.setAlbumName(String dirName); 		   // 保存到相册的文件夹
    SaveImage.setCompressQuality(int compressQuality);     // 整数品质
    SaveImage.downloadImage(String Url);  	           // 图片地址,例如 http://www.baidu.com/logo.png


