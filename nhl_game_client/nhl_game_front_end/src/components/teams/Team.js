import React from 'react'
import {Link} from 'react-router-dom'

const Team = ({team}) => {
    
    if (!team){
        return <p>Loading...</p>
    }

    const url = "/team/" + team.id

    return (
        <>
            <Link to = {url} className="name">
                {team.name}
            </Link>
        </>
    )
}

export default Team