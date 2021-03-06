import React, {useState, useEffect, useCallback} from "react";
import logo from './logo.svg';
import './App.css';
import axios from "axios";
import {useDropzone} from "react-dropzone"

const UserProfiles = () => {
const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/user-profiles").then(res => {
      console.log(res)
      setUserProfiles(res.data);
    });
  };

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  return userProfiles.map((userProfile, index) => {

    return (
    <div key={index}>
      {userProfile.userProfileImageLink ? (<img src ={'http://localhost:8080/user-profiles/'+userProfile.userProfileId+'/image/download'} />) : null }
      <br/>
      <br/>
      <h1>{userProfile.userName}</h1>
      <p>{userProfile.userProfileId}</p>
      <Dropzone userProfileId = {userProfile.userProfileId}/>
      <br/>
    </div>
    );
  });
}

function Dropzone({userProfileId}) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);
    console.log(userProfileId);

    const formData = new FormData();
    formData.append("file", file)

    axios.post(
      'http://localhost:8080/user-profiles/'+userProfileId+'/image/upload',
      formData,
      {
        headers:{
          "content-type" : "multipart/form-data"
        }
      }
    ).then( () =>{
      console.log("File uploaded successfully")
    }).catch(err => {
      console.log(err);
    })
  }, []);


  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here</p> :
          <p>Drag and drop image here, or click to select files</p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
      <UserProfiles />
    </div>
  );
}

export default App;
