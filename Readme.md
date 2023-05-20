Application scans all the video files in .mp4 .mov .mkv .flv formats, and shows them on UI, when you click the UI, it will open the stream to start playing the video

**To run with IDE**

add <sub>com.app.vstream.video_path=<FOLDER_TO_SCAN_ALL_VIDEOS></sub> to properties file

**To run as docker container**

Build image  <sub>docker build . --t vstream</sub>
Run image <sub>docker run -p 8080:8080 -e COM_APP_VSTREAM_VIDEO-PATH=/local -v "<FOLDER_TO_SCAN_ALL_VIDEOS>":"/local" --network=bridge vstream</sub>
